/**
 * La classe PartieActivity est une activité qui affiche une liste de matchs en utilisant un RecyclerView.
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
 * PartieActivity est une activité qui affiche une liste de matchs en utilisant un RecyclerView.
 */
public class PartieActivity extends AppCompatActivity implements RecyclerViewInterface {
    /**
     * Le RecyclerView qui affichera la liste des matchs.
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
        setContentView(R.layout.activity_partie);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Configure la navigation et récupère le titre
        this._nav = new Nav(this, findViewById(android.R.id.content), PartieActivity.this);
        TextView titre = findViewById(R.id.titre);

        // Mise à jour du titre et surlignement du bouton de match
        titre.setText("Les matchs");
        Button btnMatch = findViewById(R.id.matchs);
        btnMatch.setBackgroundResource(R.drawable.rounded_red);

        recyclerView = findViewById(R.id.recyclerViewPartie);
        SQLiteManager sqLiteManager = new SQLiteManager(PartieActivity.this);

        ArrayList<Partie> parties;
        try {
            parties = sqLiteManager.getParties();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        PartieAdapter partieAdapter = new PartieAdapter(this, parties, this);
        recyclerView.setAdapter(partieAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    /**
     * Gère les événements de clic sur les éléments du RecyclerView.
     *
     * @param position La position de l'élément cliqué.
     */
    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(PartieActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
