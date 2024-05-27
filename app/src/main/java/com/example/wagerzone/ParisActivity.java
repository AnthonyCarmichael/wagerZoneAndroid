
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
/**
 *
 * @author Maxime Malette
 * @version 1.0
 *
 * Cette activité permet à l'utilisateur de voir tous ses paris.
 */
public class ParisActivity extends AppCompatActivity implements RecyclerViewInterface {
    RecyclerView recyclerView;
    private Nav _nav;

    /**
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
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

    /**
     * Permet de mettre un highlight sur le bon titre du nav.
     */
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