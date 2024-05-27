package com.example.wagerzone;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONObject;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
/**
 * Activité permettant de gérer le compte d'un utilisateur.
 * Cette activité permet de consulter les informations liées a son compte,
 * ainsi que les modifié. L'utilisateur a aussi la possibilité de désactiver son compte
 * de façon permanante.
 * @author Anthony Carmichael
 * @version 1.0
 */
public class CompteActivity extends AppCompatActivity  implements View.OnClickListener{

    private static final int CAMERA_REQUEST_CODE = 102 ;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Nav _nav;
    private GestionFormUser _gestionForm;
    private TextView _username;
    private TextView _email;
    private TextView _nom;
    private TextView _prenom;
    private TextView _ddn;
    private TextView _pays;
    private TextView _ville;
    private TextView _adresse;
    private TextView _telephone;
    private Button _btnModifier;
    private Button _btnSupprimer;
    private Button _btnSend;
    private Button _btnAnnuler;
    private ScrollView _infoCompte;
    private ScrollView _form;

    /**
     * Initialisation de l'activité
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_compte);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Set le nav et récupere le titre
        this._nav = new Nav(this,findViewById(android.R.id.content),CompteActivity.this);
        TextView titre = findViewById(R.id.titre);

        // Mise a jour du titre, et surlignement de l'iconeUser
        titre.setText(R.string.compte);
        ImageButton userIcone = findViewById(R.id.userIcone);
        userIcone.setBackgroundResource(R.drawable.rounded_red);

        // Mettre les bonne informations du user dans les textviews
        setTextView();

        setButton();
    }


    /**
     * Initialisation des textView permettant l'affichage des données de l'utilisateur,
     * ainsi que l'affichage du form qui est en visibilité GONE au démarrage de l'activité
     */

    private void setTextView() {
        _username = findViewById(R.id.usernameDatabase);
        _email = findViewById(R.id.emailDatabase);
        _nom = findViewById(R.id.nomDatabase);
        _prenom = findViewById(R.id.prenomDatabase);
        _ddn = findViewById(R.id.ddnDatabase);
        _pays = findViewById(R.id.paysDatabase);
        _ville = findViewById(R.id.villeDatabase);
        _adresse = findViewById(R.id.adresseDatabase);
        _telephone = findViewById(R.id.telephoneDatabase);

        _form = findViewById(R.id.formModifier);
        _infoCompte = findViewById(R.id.infoCompte);
        setInfoCompteUser();
    }

    /**
     * Initialisation des boutons de l'activité
     */
    private void setButton(){
        _btnModifier = findViewById(R.id.modifier);
        _btnSupprimer = findViewById(R.id.supprimer);
        _btnSend = findViewById(R.id.btnSend);
        _btnAnnuler = findViewById(R.id.btnAnnuler);


        _btnModifier.setOnClickListener(this);
        _btnSupprimer.setOnClickListener(this);
        _btnSend.setOnClickListener(this);
        _btnAnnuler.setOnClickListener(this);
    }


    /**
     * Gestionnaire des actions lors d'un clic avec un bouton
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.modifier){
            // changer le layout hide info ouvre form
            _form.setVisibility(View.VISIBLE);
            if (_gestionForm == null)
            {
                _gestionForm = new GestionFormUser(this,CompteActivity.this);
                presetFormModifUser();
            }

            _infoCompte.setVisibility(View.GONE);
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 123);
        }
        else if (v.getId() == R.id.supprimer){
            // Confirmation
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(true);

            builder.setTitle("Supprimer le compte");
            builder.setMessage("Êtes-vous certain de vouloir supprimer ce compte?");

            builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            builder.setPositiveButton("Confirmer", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    desactiverUser();
                }
            });
            builder.show();

        } else if(v.getId() == R.id.btnSend){
            try {
                JSONObject data = _gestionForm.checkInput();
                if (data.length()!=0)
                    updateUser(data);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if(v.getId() == R.id.btnAnnuler){
            _form.setVisibility(View.GONE);
            _infoCompte.setVisibility(View.VISIBLE);
        }

    }


    /**
     * Gestionnaire des actions lorsqu'une réponse a été reçu après une demande de permission
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 123) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                _gestionForm.set_permissionGPS(true);
                _gestionForm.setVillePaysWithGPS();
            } else {
                _gestionForm.set_permissionGPS(false);
            }
        }
        else if (requestCode == CAMERA_REQUEST_CODE) { // permission pour la camera
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                _gestionForm.openCamera();
            }
        }
    }


    /**
     * Gestionnaire des result lorsque on lance une activité tiers
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            try {
                Bitmap image = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                _gestionForm.set_newUserIcone(findViewById(R.id.newUserIcone));
                _gestionForm.get_newUserIcone().setImageBitmap(image);
                //_nav.get_userIcone().setImageBitmap(image);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            try {
                Bundle bundle = data.getExtras();
                Bitmap image = (Bitmap) bundle.get("data");
                _gestionForm.set_newUserIcone(findViewById(R.id.newUserIcone));
                _gestionForm.get_newUserIcone().setImageBitmap(image);
                //_nav.get_userIcone().setImageBitmap(image);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Cette méthode sert a envoyer l'information a la base de donnée via notre API.
     * De plus, la variable _user du _nav est mise à jours
     * @param data Conserne le form qui a été envoyer et qu'on utilise pour mettre à jours le
     * user et de transmettre les données du form a la requête post vers le serveur de WagerZone
     */
    protected void updateUser(JSONObject data){
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            URL url = new URL("http://10.0.2.2:8000/api/updateUser");

            // Crée la connection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type","application/json");
            connection.setConnectTimeout(1000);


            data.put("id",_nav.get_user().get_id());
            // Obligatoire pour un post
            OutputStream os = connection.getOutputStream();
            os.write(data.toString().getBytes());
            os.flush();
            os.close();

            int codeReponse = connection.getResponseCode();
            String reponse = connection.getResponseMessage();
            if (codeReponse == HttpURLConnection.HTTP_OK) {
                _nav.get_user().set_name(data.get("name").toString());
                _nav.get_user().set_prenom(data.get("prenom").toString());
                _nav.get_user().set_nom(data.get("nom").toString());
                _nav.get_user().set_email(data.get("email").toString());
                _nav.get_user().set_password(data.get("password").toString());
                _nav.get_user().set_telephone(data.get("telephone").toString());
                _nav.get_user().set_adresse(data.get("adresse").toString());
                _nav.get_user().set_pays(data.get("nomPays").toString());
                _nav.get_user().set_ville(data.get("nomVille").toString());
                _nav.get_user().set_date_naissance(data.get("ddn").toString());
                setInfoCompteUser();
                _form.setVisibility(View.GONE);
                _infoCompte.setVisibility(View.VISIBLE);


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
            inscriptionError.setText(R.string.erreur_modif);
            inscriptionError.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Permet de mettre à jours les textview permettant de voir les informations liées au compte.
     */
    private void setInfoCompteUser() {
        _username.setText(_nav.get_user().get_name());
        _email.setText(_nav.get_user().get_email());
        _nom.setText(_nav.get_user().get_nom());
        _prenom.setText(_nav.get_user().get_prenom());
        _ddn.setText(_nav.get_user().get_date_naissance());
        _pays.setText(_nav.get_user().get_pays());
        _ville.setText(_nav.get_user().get_ville());
        _adresse.setText(_nav.get_user().get_adresse());
        _telephone.setText(_nav.get_user().get_telephone());
    }

    /**
     * Remplis automatiquement les champs du form avec les valeur du user
     */
    private void presetFormModifUser() {

        EditText nom = findViewById(R.id.nom);
        EditText prenom = findViewById(R.id.prenom);
        EditText username = findViewById(R.id.username);
        EditText email = findViewById(R.id.email);
        EditText telephone = findViewById(R.id.telephone);
        EditText adresse = findViewById(R.id.adresse);
        TextView ddn = findViewById(R.id.ddn);

        username.setText(_nav.get_user().get_name());
        email.setText(_nav.get_user().get_email());
        nom.setText(_nav.get_user().get_nom());
        prenom.setText(_nav.get_user().get_prenom());
        ddn.setText(_nav.get_user().get_date_naissance());
        adresse.setText(_nav.get_user().get_adresse());
        telephone.setText(_nav.get_user().get_telephone());
    }

    /**
     * Permet de désactiver l'utilisateur dans la base de données via une requête post vers l'API
     */
    protected void desactiverUser(){
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            URL url = new URL("http://10.0.2.2:8000/api/desactiverUser");

            // Crée la connection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type","application/json");
            connection.setConnectTimeout(1000);

            JSONObject data = new JSONObject();
            data.put("id",_nav.get_user().get_id());
            // Obligatoire pour un post
            OutputStream os = connection.getOutputStream();
            os.write(data.toString().getBytes());
            os.flush();
            os.close();

            int codeReponse = connection.getResponseCode();
            String reponse = connection.getResponseMessage();
            if (codeReponse == HttpURLConnection.HTTP_OK) {
                setResult(9);
                finish();
            } else {
                TextView suppressionError = findViewById(R.id.suppressionError);
                suppressionError.setText(R.string.erreur_suppression);
                suppressionError.setVisibility(View.VISIBLE);
            }
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
            TextView inscriptionError = findViewById(R.id.formError);
            inscriptionError.setText(R.string.erreur_modif);
            inscriptionError.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Permet de s'assurer que lorsque l'activité est terminée, on renvoi l'utilisateur courrant
     */
    /*
    @Override
    public void finish() {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("user", _nav.get_user());
        setResult(Activity.RESULT_OK, resultIntent);
        super.finish();
    }
*/
}