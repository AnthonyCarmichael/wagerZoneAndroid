package com.example.wagerzone;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ModificationParisActivity extends AppCompatActivity {
    private Nav _nav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modification_paris);
        this._nav = new Nav(this,findViewById(android.R.id.content),ModificationParisActivity.this);
        TextView titre = findViewById(R.id.titre);
        TextView match = findViewById(R.id.match2);
        TextView nomVisiteur = findViewById(R.id.nomVisiteur);
        TextView nomReceveur = findViewById(R.id.nomReceveur);
        TextView coteVisiteur = findViewById(R.id.coteVisiteur);
        TextView coteReceveur = findViewById(R.id.coteReceveur);
        TextView miseReceveur = findViewById(R.id.miseReceveur);
        TextView miseVisiteur = findViewById(R.id.miseVisiteur);
        titre.setText("Modification paris");
        Intent intent = getIntent();
        match.setText("Match : " + intent.getStringExtra("nomVisiteur") + " vs " + intent.getStringExtra("nomReceveur"));
        nomVisiteur.setText(intent.getStringExtra("nomVisiteur"));
        nomReceveur.setText(intent.getStringExtra("nomReceveur"));
        coteVisiteur.setText("Cote : " + String.valueOf(intent.getIntExtra("coteVisiteur", 0)));
        coteReceveur.setText("Cote : " + String.valueOf(intent.getIntExtra("coteReceveur", 0)));
        if(intent.getIntExtra("equipeMise", 2) == 0){
            miseVisiteur.setText(String.valueOf(intent.getFloatExtra("montant", 0)));
        }
        if(intent.getIntExtra("equipeMise", 2) == 1){
            miseReceveur.setText(String.valueOf(intent.getFloatExtra("montant", 0)));
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        _nav.get_home().setBackgroundResource(R.drawable.rounded_dark_red);
        _nav.get_userIcone().setBackgroundResource(R.drawable.rounded_dark_red);
        _nav.get_equipes().setBackgroundResource(R.drawable.rounded_dark_red);
        _nav.get_matchs().setBackgroundResource(R.drawable.rounded_dark_red);
        _nav.get_paris().setBackgroundResource(R.drawable.rounded_red);
    }
}