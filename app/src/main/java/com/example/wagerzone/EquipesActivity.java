package com.example.wagerzone;

import android.os.Bundle;
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

public class EquipesActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    private Nav _nav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipes);
        this._nav = new Nav(this,findViewById(android.R.id.content),EquipesActivity.this);
        TextView titre = findViewById(R.id.titre);
        titre.setText("Equipes");
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        SQLiteManager sqLiteManager = new SQLiteManager(EquipesActivity.this);
        ArrayList<Equipe> equipes;
        try {
            equipes = sqLiteManager.getEquipes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //MyAdapter myAdapter = new MyAdapter(this, equipes, this);
        ///recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}