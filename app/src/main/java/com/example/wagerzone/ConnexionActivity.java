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

public class ConnexionActivity extends AppCompatActivity  implements View.OnClickListener {

    private Button home,equipes,matchs,paris;
    private ImageButton user;
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
        if (v.getId() == R.id.home){
            v.setBackgroundResource(R.drawable.rounded_corner);
            user.setBackgroundResource(R.drawable.btn_borderless);
            finish();
        }
        if (v.getId() == R.id.equipes){
            v.setBackgroundResource(R.drawable.rounded_corner);
            user.setBackgroundResource(R.drawable.btn_borderless);
            Intent equipesIntent = new Intent(ConnexionActivity.this,ConnexionActivity.class);
            startActivity(equipesIntent);
            finish();
        }
        if (v.getId() == R.id.matchs) {
            v.setBackgroundResource(R.drawable.rounded_corner);
            user.setBackgroundResource(R.drawable.btn_borderless);
            Intent matchsIntent = new Intent(ConnexionActivity.this,ConnexionActivity.class);
            startActivity(matchsIntent);
            finish();
        }
        if (v.getId() == R.id.paris) {
            v.setBackgroundResource(R.drawable.rounded_corner);
            user.setBackgroundResource(R.drawable.btn_borderless);
            Intent parisIntent = new Intent(ConnexionActivity.this,ConnexionActivity.class);
            startActivity(parisIntent);
            finish();
        }
    }
}