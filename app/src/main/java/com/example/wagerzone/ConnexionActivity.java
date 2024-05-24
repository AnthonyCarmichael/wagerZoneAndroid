package com.example.wagerzone;

import android.content.Intent;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;

import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import java.util.UUID;

public class ConnexionActivity extends AppCompatActivity implements View.OnClickListener{

    private Nav _nav;
    private EditText _username;
    private EditText _password;
    private CheckBox _souvenir;
    private Button _btnConnecter, _btnInscription;
    private TextView _messageErreurSucces;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_connexion);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.connexion), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Set le nav et récupere le titre
        this._nav = new Nav(this,findViewById(android.R.id.content),ConnexionActivity.this);
        TextView titre = findViewById(R.id.titre);

        // Mise a jour du titre, et surlignement de l'iconeUser
        titre.setText(R.string.connexion);
        ImageButton userIcone = findViewById(R.id.userIcone);
        userIcone.setBackgroundResource(R.drawable.rounded_red);

        _messageErreurSucces = findViewById(R.id.messageErreurSucces);

        int requestCode = getIntent().getIntExtra("inscription", -1);

        setBoutons();
        if(requestCode==200)
        {
            _messageErreurSucces.setVisibility(View.VISIBLE);
        }

    }

    private void setBoutons(){
        this._btnInscription = findViewById(R.id.btnInscription);
        this._btnConnecter = findViewById(R.id.btnSeConnecter);

        _btnInscription.setOnClickListener(this);
        _btnConnecter.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnInscription){
            Intent inscriptionIntent = new Intent(this,InscriptionActivity.class);
            startActivity(inscriptionIntent);
            finish();

        }
        if (v.getId() == R.id.btnSeConnecter){
            // Asynch tentative de connexion
            _username = findViewById(R.id.username);
            _password = findViewById(R.id.mdp);
            _souvenir = findViewById(R.id.souvenir);
            verifyUser(_username.getText().toString(),_password.getText().toString(),_souvenir.isChecked());
        }
    }

    private Utilisateur verifyUser(String username, String password, Boolean souvenir) {
        Utilisateur user = null;
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            URL url = new URL("http://10.0.2.2:8000/api/connexionApi");

            // Crée la connection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type","application/json");
            connection.setConnectTimeout(1000);
            JSONObject data = new JSONObject();

            // Écriture de la requête
            data.put("username", username);
            data.put("password", password);

            if (souvenir) {
                String token = UUID.randomUUID().toString();
                data.put("souvenir", token);
                writeToken(token+";"+username);
            }

            // Obligatoire pour un post
            OutputStream os = connection.getOutputStream();
            os.write(data.toString().getBytes());
            os.flush();
            os.close();

            int codeReponse = connection.getResponseCode();
            String reponse = connection.getResponseMessage();
            if (codeReponse == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                String inputLine;

                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                JSONObject jsonResponse = new JSONObject(response.toString());
                Log.d("JSON Response", jsonResponse.toString());
                JSONObject successObject = jsonResponse.getJSONObject("SUCCESS");

                // Création du user:
                user = new Utilisateur();
                user.set_nom(successObject.getString("nom"));
                user.set_prenom(successObject.getString("prenom"));
                user.set_name(successObject.getString("name")); // USERNAME
                user.set_email(successObject.getString("name"));
                user.set_id(successObject.getInt("id"));
                user.set_date_naissance(successObject.getString("date_naissance"));
                user.set_telephone(successObject.getString("telephone"));
                user.set_adresse(successObject.getString("adresse"));
                user.set_fonds(successObject.getString("fonds"));
                //user.set_ville(successObject.getString("ville"));
                //user.set_pays(successObject.getString("ville"));

                SQLiteManager sqLiteManager = new SQLiteManager(ConnexionActivity.this);
                sqLiteManager.MAJParis(user.get_id());
                // Envoi du result à l'activité main
                Intent resultIntent = new Intent();
                resultIntent.putExtra("user", user);
                setResult(RESULT_OK, resultIntent);
                finish();

            } else if (codeReponse == 501) {
                    _messageErreurSucces.setText(R.string.erreur_compte_inactif);
                    _messageErreurSucces.setTextColor(getResources().getColor(R.color.rougeFonce));
                    _messageErreurSucces.setVisibility(View.VISIBLE);
            } else {
                _messageErreurSucces.setText(R.string.erreur_information_invalide);
                _messageErreurSucces.setTextColor(getResources().getColor(R.color.rougeFonce));
                _messageErreurSucces.setVisibility(View.VISIBLE);
            }
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
            _messageErreurSucces.setText(R.string.erreur_connexion);
            _messageErreurSucces.setTextColor(getResources().getColor(R.color.rougeFonce));
            _messageErreurSucces.setVisibility(View.VISIBLE);
        }

        return user;
    }

    private void writeToken(String token) {
        OutputStreamWriter outputStreamWriter = null;
        try{
            outputStreamWriter = new OutputStreamWriter(this.openFileOutput("tokenWagerZone.txt", this.MODE_PRIVATE));
            outputStreamWriter.write(token);
            //Toast.makeText(this,"Saved to "+this.getFilesDir()+"/"+"tokenWagerZone.txt",Toast.LENGTH_LONG).show();
        }
        catch (IOException e){
            Log.e("Execption","File write failed" + e.toString());
        }
        finally {
            if (outputStreamWriter != null){
                try{
                    outputStreamWriter.close();
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

}