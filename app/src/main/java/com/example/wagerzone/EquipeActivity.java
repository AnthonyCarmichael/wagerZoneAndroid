package com.example.wagerzone;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
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

public class EquipeActivity extends AppCompatActivity implements RecyclerViewInterface{
    RecyclerView recyclerView;
    private Nav _nav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_equipe);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Set le nav et récupere le titre
        this._nav = new Nav(this,findViewById(android.R.id.content),EquipeActivity.this);
        TextView titre = findViewById(R.id.titre);

        // Mise a jour du titre, et surlignement de l'iconeUser
        titre.setText(R.string.equipe);
        Button btnEquipe = findViewById(R.id.equipes);
        btnEquipe.setBackgroundResource(R.drawable.rounded_red);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewEquipe);
        SQLiteManager sqLiteManager = new SQLiteManager(EquipeActivity.this);

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
    protected void onResume() {
        super.onResume();
        _nav.get_home().setBackgroundResource(R.drawable.rounded_dark_red);
        _nav.get_userIcone().setBackgroundResource(R.drawable.rounded_dark_red);
        _nav.get_equipes().setBackgroundResource(R.drawable.rounded_dark_red);
        _nav.get_matchs().setBackgroundResource(R.drawable.rounded_dark_red);
        _nav.get_paris().setBackgroundResource(R.drawable.rounded_red);
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(EquipeActivity.this, MainActivity.class);
        startActivity(intent);
    }
}