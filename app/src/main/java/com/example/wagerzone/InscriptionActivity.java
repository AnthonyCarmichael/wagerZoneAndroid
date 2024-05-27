package com.example.wagerzone;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import android.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Cette classe gère l'inscription de l'utilisateur via un form d'inscription et une requête POST avec
 * l'API
 *  @author Anthony Carmichael
 *  @version 1.0
 */
public class InscriptionActivity extends AppCompatActivity  implements View.OnClickListener{

    private static final int CAMERA_PERM_CODE = 101;
    private static final int CAMERA_REQUEST_CODE = 102 ;

    private static final int PICK_IMAGE_REQUEST = 1;

    private Nav _nav;
    private List<Pays> _pays;
    private List<Ville> _villes;
    private ArrayList<String> _nomVilles;
    private ArrayList<String> _nomPays;
    private Spinner _spinnerVille;
    private Spinner _spinnerPays;
    private String _gpsVille;
    private String _gpsPays;
    private Boolean _permissionGPS;

    private ImageView _newUserIcone;

    // button
    private Button _btnPhoto;
    private Button _btnFichier;
    private Button _btnSend;
    private Button _btnDdn;
    private Calendar _calendrier;
    private Calendar _dateLimite;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_inscription);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Set le nav et récupere le titre
        this._nav = new Nav(this,findViewById(android.R.id.content),InscriptionActivity.this);
        TextView titre = findViewById(R.id.titre);

        // Mise a jour du titre, et surlignement de l'iconeUser
        titre.setText(R.string.inscription);
        ImageButton userIcone = findViewById(R.id.userIcone);
        userIcone.setBackgroundResource(R.drawable.rounded_red);

        // Set les spinner
        set_spinnerPays();
        set_spinnerVille();
        // Permission geolocalisation.
        // Le callback permet d'attendre que l'utilisateur donne le droit ou non au gps pour loader ensuite la page
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 123);
        set_calendrier();
        setButton();

    }

    private void fetchVille(int idPays) {
        SQLiteManager sqLiteManager = new SQLiteManager(InscriptionActivity.this);
        _villes = sqLiteManager.fetchAllVillesByPaysId(idPays);
    }

    private void fetchPays() {
        SQLiteManager sqLiteManager = new SQLiteManager(InscriptionActivity.this);
        _pays = sqLiteManager.fetchAllPays();
    }

    private void set_spinnerVille(){
        // Set les spinner:
        fetchVille(1);
        _nomVilles = new ArrayList<>();
        for (Ville ville: _villes) {
            _nomVilles.add(ville.get_nom_ville());
        }
        ArrayAdapter<String> arrayAdapterVilles=new ArrayAdapter<String>(InscriptionActivity.this,
                android.R.layout.simple_spinner_dropdown_item, _nomVilles);

        Spinner spinnerVille= findViewById(R.id.villes);

        spinnerVille.setAdapter(arrayAdapterVilles);
        this._spinnerVille = spinnerVille;
        _spinnerPays.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                _villes.clear();
                _nomVilles.clear();
                fetchVille(_pays.get(position).get_id_pays());
                for (Ville ville: _villes) {
                    _nomVilles.add(ville.get_nom_ville());
                }
                ArrayAdapter<String> arrayAdapterVilles=new ArrayAdapter<String>(InscriptionActivity.this,
                        android.R.layout.simple_spinner_dropdown_item, _nomVilles);
                _spinnerVille.setAdapter(arrayAdapterVilles);
                if (_gpsVille!= null && _gpsPays.equals(_pays.get(position).get_nom_pays())){
                    _nomVilles.add(_gpsVille);
                    arrayAdapterVilles.notifyDataSetChanged();
                    setSelectedItem(_spinnerVille,_gpsVille);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });
    }

    private void set_spinnerPays(){
        // Set les spinner:
        fetchPays();
        _nomPays= new ArrayList<>();
        for (Pays pays: _pays) {
            _nomPays.add(pays.get_nom_pays());
        }
        ArrayAdapter<String> arrayAdapterVilles=new ArrayAdapter<String>(InscriptionActivity.this,
                android.R.layout.simple_spinner_dropdown_item, _nomPays);

        Spinner spinnerPays= findViewById(R.id.pays);

        spinnerPays.setAdapter(arrayAdapterVilles);
        this._spinnerPays = spinnerPays;
    }

    private void setVillePaysWithGPS() {
        GPStracker gpsTracker = new GPStracker(this);
        Location location = gpsTracker.getLocation();
        Geocoder geocoder = new Geocoder(this, Locale.FRENCH);
        try {
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);
                String cityName = address.getLocality(); // Ville
                String countryName = address.getCountryName(); // Pays

                // Log the city and country
                Log.d("AddressInfo", "Ville: " + cityName);
                Log.d("AddressInfo", "Pays: " + countryName);

                // Set les spinners!
                int cpt =0;
                boolean villeTrouve = false;
                boolean paysTrouve = false;
                _gpsPays=countryName;
                int idPaysTrouve = 0;
                for (Pays pays : _pays) {
                    if (pays.get_nom_pays().equals(countryName)) {
                        setSelectedItem(_spinnerPays, pays.get_nom_pays());
                        paysTrouve =true;
                        idPaysTrouve = pays.get_id_pays();
                        cpt =0;
                        for (Ville ville : _villes) {
                            if (ville.get_nom_ville().equals(cityName)) {
                                setSelectedItem(_spinnerVille, ville.get_nom_ville());
                                villeTrouve=true;
                                break;
                            }
                            else
                                cpt++;
                        }
                        break;
                    }
                    else
                        cpt++;
                }
                if (!paysTrouve){
                    _nomPays.add(countryName);
                    Pays paysGPS = new Pays();
                    paysGPS.set_nom_pays(countryName);
                    _pays.add(paysGPS);
                    ArrayAdapter<String> arrayAdapterPays=new ArrayAdapter<String>(InscriptionActivity.this,
                            android.R.layout.simple_spinner_dropdown_item, _nomPays);
                    _spinnerPays.setAdapter(arrayAdapterPays);
                    setSelectedItem(_spinnerPays,countryName);
                }
                if (!villeTrouve){
                    _gpsVille=cityName;
                    Ville villeGPS =new Ville();
                    villeGPS.set_nom_ville(cityName);
                    if (paysTrouve && idPaysTrouve != 0)
                        villeGPS.set_id_pays(idPaysTrouve);
                    _villes.add(villeGPS);

                    _nomVilles.add(cityName);
                    ArrayAdapter<String> arrayAdapterVilles=new ArrayAdapter<String>(InscriptionActivity.this,
                            android.R.layout.simple_spinner_dropdown_item, _nomVilles);
                    _spinnerVille.setAdapter(arrayAdapterVilles);
                    setSelectedItem(_spinnerVille,cityName);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("GeocoderError", "Unable to get address from location", e);
        }
    }

    private void setSelectedItem(Spinner spinner, String value) {
        ArrayAdapter<String> adapter = (ArrayAdapter<String>) spinner.getAdapter();
        int position = adapter.getPosition(value);
        if (position >= 0) {
            spinner.setSelection(position);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 123) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                _permissionGPS = true;
                setVillePaysWithGPS();
            } else {
                _permissionGPS = false;
            }
        }
        else if (requestCode == CAMERA_REQUEST_CODE) { // permission pour la camera
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            }
        }
    }

    private void askCameraPermission(){
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},CAMERA_REQUEST_CODE);
        }
        else {
            openCamera();
        }
    }

    private void openCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
    }

    private void setButton(){
        _btnSend = findViewById(R.id.btnSend);
        _btnFichier = findViewById(R.id.btnFichier);
        _btnPhoto = findViewById(R.id.btnPhoto);
        _btnDdn = findViewById(R.id.chooseDateButton);

        _btnPhoto.setOnClickListener(this);
        _btnFichier.setOnClickListener(this);
        _btnSend.setOnClickListener(this);
        _btnDdn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnPhoto){
            askCameraPermission();
        }
        else if(v.getId() == R.id.btnFichier){
            openGallery();
        }
        else if(v.getId() == R.id.btnSend){
            try {
                JSONObject data = checkInput();
                if (data.length()!=0)
                    insertUser(data);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (v.getId()==R.id.chooseDateButton) {
            int year = _calendrier.get(Calendar.YEAR);
            int month = _calendrier.get(Calendar.MONTH);
            int dayOfMonth = _calendrier.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(InscriptionActivity.this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            TextView ddn = findViewById(R.id.ddn);
                            String date = year+"-"+(monthOfYear + 1) +"-"+dayOfMonth;
                            ddn.setText(date);
                        }
                    }, year, month, dayOfMonth);
            datePickerDialog.getDatePicker().setMaxDate(_dateLimite.getTimeInMillis());
            datePickerDialog.show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            try {
                Bitmap image = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                _newUserIcone = findViewById(R.id.newUserIcone);
                _newUserIcone.setImageBitmap(image);
                //_nav.get_userIcone().setImageBitmap(image);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            try {
                Bundle bundle = data.getExtras();
                Bitmap image = (Bitmap) bundle.get("data");
                _newUserIcone = findViewById(R.id.newUserIcone);
                _newUserIcone.setImageBitmap(image);
                //_nav.get_userIcone().setImageBitmap(image);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private JSONObject checkInput() throws JSONException {
        JSONObject form = new JSONObject();
        Boolean badForm = false;

        EditText nom = findViewById(R.id.nom);
        EditText prenom = findViewById(R.id.prenom);
        EditText username = findViewById(R.id.username);
        EditText mdp = findViewById(R.id.mdp);
        EditText mdp2 = findViewById(R.id.mdp2);
        EditText email = findViewById(R.id.email);
        EditText telephone = findViewById(R.id.telephone);
        EditText adresse = findViewById(R.id.adresse);
        TextView ddn = findViewById(R.id.ddn);

        TextView nomError = findViewById(R.id.nomError);
        TextView prenomError = findViewById(R.id.prenomError);
        TextView usernameError = findViewById(R.id.usernameError);
        TextView mdpError = findViewById(R.id.mdpError);
        TextView mdp2Error = findViewById(R.id.mdp2Error);
        TextView emailError = findViewById(R.id.emailError);
        TextView telephoneError = findViewById(R.id.telephoneError);
        TextView adresseError = findViewById(R.id.adresseError);
        TextView ddnError = findViewById(R.id.ddnError);
        TextView formError = findViewById(R.id.formError);

        // Reset tout les messages d'erreurs:
        usernameError.setVisibility(View.GONE);
        mdpError.setVisibility(View.GONE);
        mdp2Error.setVisibility(View.GONE);
        emailError.setVisibility(View.GONE);
        telephoneError.setVisibility(View.GONE);
        adresseError.setVisibility(View.GONE);
        nomError.setVisibility(View.GONE);
        prenomError.setVisibility(View.GONE);
        ddnError.setVisibility(View.GONE);
        formError.setVisibility(View.GONE);


        if (nom.getText().toString().isEmpty()){
            nomError.setVisibility(View.VISIBLE);
            badForm = true;
        }
        if (prenom.getText().toString().isEmpty()){
            prenomError.setVisibility(View.VISIBLE);
            badForm = true;
        }
        if (username.getText().toString().isEmpty()){
            usernameError.setVisibility(View.VISIBLE);
            badForm = true;
        }
        if (mdp.getText().toString().isEmpty()){
            mdpError.setVisibility(View.VISIBLE);
            mdpError.setText(R.string.obligatoire);
            badForm = true;
        } else if (!mdp.getText().toString().equals(mdp2.getText().toString())) {
            mdpError.setVisibility(View.VISIBLE);
            mdpError.setText(R.string.pas_pareil);
            badForm = true;
        }
        if (mdp2.getText().toString().isEmpty()){
            mdp2Error.setVisibility(View.VISIBLE);
            badForm = true;
        }
        if (email.getText().toString().isEmpty()){
            emailError.setText(R.string.obligatoire);
            emailError.setVisibility(View.VISIBLE);
            badForm = true;
        } else if (!emailValidator(email.getText().toString())){
            emailError.setText(R.string.invalide);
            emailError.setVisibility(View.VISIBLE);
            badForm = true;
        }
        if (adresse.getText().toString().isEmpty()){
            adresseError.setVisibility(View.VISIBLE);
            badForm = true;
        }
        if (telephone.getText().toString().isEmpty()) {
            telephoneError.setText(R.string.obligatoire);
            telephoneError.setVisibility(View.VISIBLE);
            badForm = true;
        } else if (!phoneValidator(telephone.getText().toString())) {
            telephoneError.setText(R.string.invalide);
            telephoneError.setVisibility(View.VISIBLE);
            badForm = true;
        }
        if (ddn.getText().toString().isEmpty()) {
            ddnError.setText(R.string.obligatoire);
            ddnError.setVisibility(View.VISIBLE);
            badForm = true;
        }
        if(badForm){
            formError.setText(R.string.revoirForm);
            formError.setVisibility(View.VISIBLE);
            return form;
        }
        else {
            form.put("name",username.getText().toString());
            form.put("nom",nom.getText().toString());
            form.put("prenom",prenom.getText().toString());
            form.put("email",email.getText().toString());
            form.put("password",mdp.getText().toString());
            form.put("telephone",telephone.getText().toString());
            form.put("adresse",adresse.getText().toString());
            form.put("nomPays",_spinnerPays.getSelectedItem());
            form.put("nomVille",_spinnerVille.getSelectedItem());
            form.put("ddn",ddn.getText().toString());
            if (_newUserIcone.getDrawable() != null) {
                byte[] byteArray = convertImageViewToByteArr(_newUserIcone);
                String image = Base64.encodeToString(byteArray,Base64.DEFAULT);
                form.put("image",image);
            }
            return form;
        }

    }

    public boolean emailValidator(String email)
    {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean phoneValidator(String phone)
    {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^(\\d{3}[- .]?){2}\\d{4}$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(phone);
        return matcher.matches();
    }

    public void insertUser(JSONObject data)
    {
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            URL url = new URL("http://10.0.2.2:8000/api/insertNewUser");

            // Crée la connection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type","application/json");
            connection.setConnectTimeout(1000);

            // Obligatoire pour un post
            OutputStream os = connection.getOutputStream();
            os.write(data.toString().getBytes());
            os.flush();
            os.close();

            int codeReponse = connection.getResponseCode();
            String reponse = connection.getResponseMessage();
            if (codeReponse == HttpURLConnection.HTTP_OK) {
                Intent resultIntent = new Intent();
                setResult(200, resultIntent);
                finish();

            } else if (codeReponse == 501) {
                TextView inscriptionError = findViewById(R.id.formError);
                inscriptionError.setText(R.string.erreur_email);
                inscriptionError.setVisibility(View.VISIBLE);
            } else if (codeReponse == 502) {
                TextView inscriptionError = findViewById(R.id.formError);
                inscriptionError.setText(R.string.erreur_username);
                inscriptionError.setVisibility(View.VISIBLE);
            } else {
                TextView inscriptionError = findViewById(R.id.formError);
                inscriptionError.setText(R.string.erreur_inscription);
                inscriptionError.setVisibility(View.VISIBLE);
            }
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
            TextView inscriptionError = findViewById(R.id.formError);
            inscriptionError.setText(R.string.erreur_inscription);
            inscriptionError.setVisibility(View.VISIBLE);
        }

    }

    private void set_calendrier(){
        _calendrier = Calendar.getInstance();
        _dateLimite = Calendar.getInstance();
        _dateLimite.add(Calendar.YEAR, -18);
    }

    private byte[] convertImageViewToByteArr(ImageView image){
        image.setDrawingCacheEnabled(true);
        image.buildDrawingCache();
        Bitmap bitmap = image.getDrawingCache();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        if (image.getDrawingCache().getConfig() == Bitmap.Config.ARGB_8888) {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        } else {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        }

        byte[] byteArray = byteArrayOutputStream.toByteArray();

        return byteArray;
    }
    
}