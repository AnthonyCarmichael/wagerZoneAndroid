package com.example.wagerzone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button home,equipes,matchs,paris;
    private ImageButton user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
            //TEST PUSH ANTHO
            //Test Richard
            //Test push max
        });
        setNav();
    }

    protected void setNav(){
        user = findViewById(R.id.userIcone);
        home = findViewById(R.id.home);
        equipes = findViewById(R.id.equipes);
        matchs = findViewById(R.id.matchs);
        paris = findViewById(R.id.paris);

        user.setOnClickListener(this);
        home.setOnClickListener(this);
        equipes.setOnClickListener(this);
        matchs.setOnClickListener(this);
        paris.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.userIcone){
            Intent connexionIntent = new Intent(MainActivity.this,ConnexionActivity.class);
            //Avant de lancer l'activité
            v.setBackgroundResource(R.drawable.rounded_corner);
            home.setBackgroundResource(R.drawable.btn_borderless);
            startActivity(connexionIntent);

        }
        if (v.getId() == R.id.equipes){
            Intent equipesIntent = new Intent(MainActivity.this,ConnexionActivity.class);
            v.setBackgroundResource(R.drawable.rounded_corner);
            home.setBackgroundResource(R.drawable.btn_borderless);
            startActivity(equipesIntent);
        }
        if (v.getId() == R.id.matchs) {
            Intent matchsIntent = new Intent(MainActivity.this,ConnexionActivity.class);
            v.setBackgroundResource(R.drawable.rounded_corner);
            home.setBackgroundResource(R.drawable.btn_borderless);
            startActivity(matchsIntent);
        }
        if (v.getId() == R.id.paris) {
            Intent parisIntent = new Intent(MainActivity.this,ConnexionActivity.class);
            v.setBackgroundResource(R.drawable.rounded_corner);
            home.setBackgroundResource(R.drawable.btn_borderless);
            startActivity(parisIntent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Quand on reviens au main
        home.setBackgroundResource(R.drawable.rounded_corner);
        findViewById(R.id.userIcone).setBackgroundResource(R.drawable.btn_borderless);
        findViewById(R.id.equipes).setBackgroundResource(R.drawable.btn_borderless);
        findViewById(R.id.matchs).setBackgroundResource(R.drawable.btn_borderless);
        findViewById(R.id.paris).setBackgroundResource(R.drawable.btn_borderless);
    }
}