package com.example.wagerzone;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;

public class PartieActivity extends AppCompatActivity implements RecyclerViewInterface{
    RecyclerView recyclerView;
    private Nav _nav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_partie);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Set le nav et récupere le titre
        this._nav = new Nav(this,findViewById(android.R.id.content),PartieActivity.this);
        TextView titre = findViewById(R.id.titre);

        // Mise a jour du titre, et surlignement de l'iconeUser
        titre.setText("Les matchs");
        Button btnEquipe = findViewById(R.id.match);
        btnEquipe.setBackgroundResource(R.drawable.rounded_red);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewPartie);
        SQLiteManager sqLiteManager = new SQLiteManager(PartieActivity.this);

        ArrayList<Equipe> equipes;
        try {
            equipes = sqLiteManager.getEquipes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        EquipeAdapter equipeAdapter = new EquipeAdapter(this, equipes, this);
        recyclerView.setAdapter(equipeAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(PartieActivity.this, MainActivity.class);
        startActivity(intent);
    }
}