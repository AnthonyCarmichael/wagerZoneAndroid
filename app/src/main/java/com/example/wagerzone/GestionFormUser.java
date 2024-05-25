package com.example.wagerzone;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
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
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GestionFormUser extends AppCompatActivity implements View.OnClickListener{
    private static final int CAMERA_PERM_CODE = 101;
    private static final int CAMERA_REQUEST_CODE = 102 ;

    private static final int PICK_IMAGE_REQUEST = 1;
    private Context _context;
    private Activity _activity;
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

    public GestionFormUser(Context context,Activity activity) {
        this._context = context;
        this._activity = activity;
        this.setForm();
    }

    public Context get_context() {
        return _context;
    }

    public void set_context(Context _context) {
        this._context = _context;
    }

    public Activity get_activity() {
        return _activity;
    }

    public void set_activity(Activity _activity) {
        this._activity = _activity;
    }

    public List<Pays> get_pays() {
        return _pays;
    }

    public void set_pays(List<Pays> _pays) {
        this._pays = _pays;
    }

    public List<Ville> get_villes() {
        return _villes;
    }

    public void set_villes(List<Ville> _villes) {
        this._villes = _villes;
    }

    public ArrayList<String> get_nomVilles() {
        return _nomVilles;
    }

    public void set_nomVilles(ArrayList<String> _nomVilles) {
        this._nomVilles = _nomVilles;
    }

    public ArrayList<String> get_nomPays() {
        return _nomPays;
    }

    public void set_nomPays(ArrayList<String> _nomPays) {
        this._nomPays = _nomPays;
    }

    public Spinner get_spinnerVille() {
        return _spinnerVille;
    }

    public void set_spinnerVille(Spinner _spinnerVille) {
        this._spinnerVille = _spinnerVille;
    }

    public Spinner get_spinnerPays() {
        return _spinnerPays;
    }

    public void set_spinnerPays(Spinner _spinnerPays) {
        this._spinnerPays = _spinnerPays;
    }

    public String get_gpsVille() {
        return _gpsVille;
    }

    public void set_gpsVille(String _gpsVille) {
        this._gpsVille = _gpsVille;
    }

    public String get_gpsPays() {
        return _gpsPays;
    }

    public void set_gpsPays(String _gpsPays) {
        this._gpsPays = _gpsPays;
    }

    public Boolean get_permissionGPS() {
        return _permissionGPS;
    }

    public void set_permissionGPS(Boolean _permissionGPS) {
        this._permissionGPS = _permissionGPS;
    }

    public ImageView get_newUserIcone() {
        return _newUserIcone;
    }

    public void set_newUserIcone(ImageView _newUserIcone) {
        this._newUserIcone = _newUserIcone;
    }

    public Button get_btnPhoto() {
        return _btnPhoto;
    }

    public void set_btnPhoto(Button _btnPhoto) {
        this._btnPhoto = _btnPhoto;
    }

    public Button get_btnFichier() {
        return _btnFichier;
    }

    public void set_btnFichier(Button _btnFichier) {
        this._btnFichier = _btnFichier;
    }

    public Button get_btnSend() {
        return _btnSend;
    }

    public void set_btnSend(Button _btnSend) {
        this._btnSend = _btnSend;
    }

    public Button get_btnDdn() {
        return _btnDdn;
    }

    public void set_btnDdn(Button _btnDdn) {
        this._btnDdn = _btnDdn;
    }

    public Calendar get_calendrier() {
        return _calendrier;
    }

    public void set_calendrier(Calendar _calendrier) {
        this._calendrier = _calendrier;
    }

    public Calendar get_dateLimite() {
        return _dateLimite;
    }

    public void set_dateLimite(Calendar _dateLimite) {
        this._dateLimite = _dateLimite;
    }

    public void fetchVille(int idPays) {
        SQLiteManager sqLiteManager = new SQLiteManager(_context);
        _villes = sqLiteManager.fetchAllVillesByPaysId(idPays);
    }

    public void fetchPays() {
        SQLiteManager sqLiteManager = new SQLiteManager(_context);
        _pays = sqLiteManager.fetchAllPays();
    }

    public void set_spinnerVille(){
        // Set les spinner:
        fetchVille(1);
        _nomVilles = new ArrayList<>();
        for (Ville ville: _villes) {
            _nomVilles.add(ville.get_nom_ville());
        }
        ArrayAdapter<String> arrayAdapterVilles=new ArrayAdapter<String>(_context,
                android.R.layout.simple_spinner_dropdown_item, _nomVilles);

        Spinner spinnerVille= _activity.findViewById(R.id.villes);

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
                ArrayAdapter<String> arrayAdapterVilles=new ArrayAdapter<String>(_context,
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

    public void set_spinnerPays(){
        // Set les spinner:
        fetchPays();
        _nomPays= new ArrayList<>();
        for (Pays pays: _pays) {
            _nomPays.add(pays.get_nom_pays());
        }
        ArrayAdapter<String> arrayAdapterVilles=new ArrayAdapter<String>(_context,
                android.R.layout.simple_spinner_dropdown_item, _nomPays);

        Spinner spinnerPays= _activity.findViewById(R.id.pays);

        spinnerPays.setAdapter(arrayAdapterVilles);
        this._spinnerPays = spinnerPays;
    }

    public void setVillePaysWithGPS() {
        GPStracker gpsTracker = new GPStracker(_context);
        Location location = gpsTracker.getLocation();
        Geocoder geocoder = new Geocoder(_context, Locale.FRENCH);
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
                    ArrayAdapter<String> arrayAdapterPays=new ArrayAdapter<String>(_context,
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
                    ArrayAdapter<String> arrayAdapterVilles=new ArrayAdapter<String>(_context,
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

    public void setSelectedItem(Spinner spinner, String value) {
        ArrayAdapter<String> adapter = (ArrayAdapter<String>) spinner.getAdapter();
        int position = adapter.getPosition(value);
        if (position >= 0) {
            spinner.setSelection(position);
        }
    }

    public void askCameraPermission(){
        if (ContextCompat.checkSelfPermission(_context, android.Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(_activity,new String[]{Manifest.permission.CAMERA},CAMERA_REQUEST_CODE);
        }
        else {
            openCamera();
        }
    }

    public void openCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        _activity.startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
    }

    public void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        _activity.startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
    }

    public JSONObject checkInput() throws JSONException {
        JSONObject form = new JSONObject();
        Boolean badForm = false;

        EditText nom = _activity.findViewById(R.id.nom);
        EditText prenom = _activity.findViewById(R.id.prenom);
        EditText username = _activity.findViewById(R.id.username);
        EditText mdp = _activity.findViewById(R.id.mdp);
        EditText mdp2 = _activity.findViewById(R.id.mdp2);
        EditText email = _activity.findViewById(R.id.email);
        EditText telephone = _activity.findViewById(R.id.telephone);
        EditText adresse = _activity.findViewById(R.id.adresse);
        TextView ddn = _activity.findViewById(R.id.ddn);

        TextView nomError = _activity.findViewById(R.id.nomError);
        TextView prenomError = _activity.findViewById(R.id.prenomError);
        TextView usernameError = _activity.findViewById(R.id.usernameError);
        TextView mdpError = _activity.findViewById(R.id.mdpError);
        TextView mdp2Error = _activity.findViewById(R.id.mdp2Error);
        TextView emailError = _activity.findViewById(R.id.emailError);
        TextView telephoneError = _activity.findViewById(R.id.telephoneError);
        TextView adresseError = _activity.findViewById(R.id.adresseError);
        TextView ddnError = _activity.findViewById(R.id.ddnError);
        TextView formError = _activity.findViewById(R.id.formError);

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
                _activity.setResult(200);
                _activity.finish();

            } else if (codeReponse == 501) {
                TextView inscriptionError = _activity.findViewById(R.id.formError);
                inscriptionError.setText(R.string.erreur_email);
                inscriptionError.setVisibility(View.VISIBLE);
            } else if (codeReponse == 502) {
                TextView inscriptionError = _activity.findViewById(R.id.formError);
                inscriptionError.setText(R.string.erreur_username);
                inscriptionError.setVisibility(View.VISIBLE);
            } else {
                TextView inscriptionError = _activity.findViewById(R.id.formError);
                inscriptionError.setText(R.string.erreur_inscription);
                inscriptionError.setVisibility(View.VISIBLE);
            }
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
            TextView inscriptionError = _activity.findViewById(R.id.formError);
            inscriptionError.setText(R.string.erreur_inscription);
            inscriptionError.setVisibility(View.VISIBLE);
        }
    }

    public void set_calendrier(){
        _calendrier = Calendar.getInstance();
        _dateLimite = Calendar.getInstance();
        _dateLimite.add(Calendar.YEAR, -18);
    }

    private void setForm(){
        // Set les spinner
        set_spinnerPays();
        set_spinnerVille();
        // Permission geolocalisation.
        // Le callback permet d'attendre que l'utilisateur donne le droit ou non au gps pour loader ensuite la page

        set_calendrier();
        setButton();
    }

    private void setButton(){
        _btnSend = _activity.findViewById(R.id.btnSend);
        _btnFichier = _activity.findViewById(R.id.btnFichier);
        _btnPhoto = _activity.findViewById(R.id.btnPhoto);
        _btnDdn = _activity.findViewById(R.id.chooseDateButton);

        _btnPhoto.setOnClickListener(this);
        _btnFichier.setOnClickListener(this);
        //_btnSend.setOnClickListener(this);
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
        else if(v.getId() == R.id.btnSend && _activity.getClass() == InscriptionActivity.class){
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

            DatePickerDialog datePickerDialog = new DatePickerDialog(_context,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            TextView ddn = _activity.findViewById(R.id.ddn);
                            String date = year+"-"+(monthOfYear + 1) +"-"+dayOfMonth;
                            ddn.setText(date);
                        }
                    }, year, month, dayOfMonth);
            datePickerDialog.getDatePicker().setMaxDate(_dateLimite.getTimeInMillis());
            datePickerDialog.show();
        }
    }



}
