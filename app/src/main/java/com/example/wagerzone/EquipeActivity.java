/**
 * La classe EquipeActivity est une activité qui affiche une liste d'équipes en utilisant un RecyclerView.
 * Elle implémente l'interface RecyclerViewInterface pour gérer les événements de clic sur les éléments.
 */
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

/**
 * EquipeActivity est une activité qui affiche une liste d'équipes en utilisant un RecyclerView.
 */
public class EquipeActivity extends AppCompatActivity implements RecyclerViewInterface {
    /**
     * Le RecyclerView qui affichera la liste des équipes.
     */
    RecyclerView recyclerView;

    /**
     * La classe d'aide à la navigation.
     */
    private Nav _nav;

    /**
     * Appelé lorsque l'activité est créée pour la première fois.
     *
     * @param savedInstanceState Si l'activité est ré-initialisée après avoir été précédemment arrêtée, ce Bundle contient les données les plus récentes fournies dans onSaveInstanceState(Bundle). Note : Sinon, il est null.
     */
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

        // Configure la navigation et récupère le titre
        this._nav = new Nav(this, findViewById(android.R.id.content), EquipeActivity.this);
        TextView titre = findViewById(R.id.titre);

        // Mise à jour du titre et surlignement du bouton d'équipe
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

    /**
     * Gère les événements de clic sur les éléments du RecyclerView.
     *
     * @param position La position de l'élément cliqué.
     */
    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(EquipeActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
