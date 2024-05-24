package com.example.wagerzone;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CompteActivity extends AppCompatActivity {

    private Nav _nav;
    private TextView _username;
    private TextView _email;
    private TextView _nom;
    private TextView _prenom;
    private TextView _ddn;
    private TextView _pays;
    private TextView _ville;
    private TextView _adresse;
    private TextView _telephone;
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
    }

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
}