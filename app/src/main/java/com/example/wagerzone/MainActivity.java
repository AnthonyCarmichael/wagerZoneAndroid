package com.example.wagerzone;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private Nav _nav;
    private GestionFonds fonds;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
            //TEST PUSH ANTHO
            //Test Richard
            //Test push max
            //Test push JL
        });
        _nav = new Nav(this, findViewById(android.R.id.content),MainActivity.this);
        TextView titre = findViewById(R.id.titre);
        titre.setText(R.string.accueil);
        Button notification = findViewById(R.id.notificationButton);
        notification.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                GestionNotifications notif = new GestionNotifications(MainActivity.this);
                notif.creationNouveauParis("Demain");
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        // Quand on reviens au main
        _nav.get_home().setBackgroundResource(R.drawable.rounded_red);
        _nav.get_userIcone().setBackgroundResource(R.drawable.rounded_dark_red);
        _nav.get_equipes().setBackgroundResource(R.drawable.rounded_dark_red);
        _nav.get_matchs().setBackgroundResource(R.drawable.rounded_dark_red);
        _nav.get_paris().setBackgroundResource(R.drawable.rounded_dark_red);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 2){
            Intent connexionIntent = new Intent(this,ConnexionActivity.class);
            //Avant de lancer l'activité
            startActivityForResult(connexionIntent, 3);
        }
        if (requestCode == 3 && resultCode == RESULT_OK) {
            // Récupérer l'objet User de l'Intent
            if (data != null && data.hasExtra("user")) {
                Utilisateur user = (Utilisateur) data.getSerializableExtra("user");

                if (user != null) {
                    TextView messageErreurSuccesMain = _nav.get_messageErreurSuccesMain();
                    messageErreurSuccesMain.setText(R.string.succesConnection);
                    messageErreurSuccesMain.setTextColor(getResources().getColor(R.color.vertFonce));
                    messageErreurSuccesMain.setVisibility(View.VISIBLE);
                    _nav.set_user(user);
                    if (_nav.get_user().get_image().length > 3) {
                        Bitmap newicone = BitmapFactory.decodeByteArray(_nav.get_user().get_image(), 0, _nav.get_user().get_image().length);
                        _nav.get_userIcone().setImageBitmap(newicone);
                    }

                }

            }
        }
        if (resultCode == 9){ // Déconnexion
            _nav.set_user(null);
            fonds.deleteFonds();
            TextView messageErreurSuccesMain = _nav.get_messageErreurSuccesMain();
            messageErreurSuccesMain.setVisibility(View.INVISIBLE);
            _nav.get_userIcone().setImageResource(R.drawable.user);
        }
        if (resultCode == 200){ // Inscription redirection vers activité connexion
            // Temporaire
            TextView messageErreurSuccesMain = _nav.get_messageErreurSuccesMain();
            messageErreurSuccesMain.setText(R.string.vous_avez_bien_t_inscrit);
            messageErreurSuccesMain.setTextColor(getResources().getColor(R.color.vertFonce));
            messageErreurSuccesMain.setVisibility(View.VISIBLE);
            /* Bug pour l'instant
            Intent connexionIntent = new Intent(this,ConnexionActivity.class);
            connexionIntent.putExtra("user", _nav.get_user());
            connexionIntent.putExtra("inscription", 200);
            startActivityForResult(connexionIntent, 200);
            */
        }
        if (data != null && data.hasExtra("user")) {
            Utilisateur user = (Utilisateur) data.getSerializableExtra("user");
            _nav.set_user(user);

        }

    }
}