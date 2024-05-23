package com.example.wagerzone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

public class ParisActivity extends AppCompatActivity implements RecyclerViewInterface {
    RecyclerView recyclerView;
    private Nav _nav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paris);
        this._nav = new Nav(this,findViewById(android.R.id.content),ParisActivity.this);
        TextView titre = findViewById(R.id.titre);
        titre.setText("Mes paris");
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        SQLiteManager sqLiteManager = new SQLiteManager(ParisActivity.this);


        ArrayList<Paris> paris;
        try {
            paris = sqLiteManager.getParis();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        MyAdapter myAdapter = new MyAdapter(this, paris, this);
        recyclerView.setAdapter(myAdapter);
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

    }
}