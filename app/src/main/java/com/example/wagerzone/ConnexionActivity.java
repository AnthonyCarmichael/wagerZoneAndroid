package com.example.wagerzone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ConnexionActivity extends AppCompatActivity implements View.OnClickListener{

    private Nav _nav;
    private Button _btnConnecter, _btnInscription;
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

        setBoutons();

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
        }
    }


}