package com.example.wagerzone;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Nav _nav;

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
            //Test push JL
        });
        _nav = new Nav(this, findViewById(android.R.id.content),MainActivity.this);
        TextView titre = findViewById(R.id.titre);
        titre.setText(R.string.accueil);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Quand on reviens au main
        _nav.get_home().setBackgroundResource(R.drawable.rounded_corner);
        _nav.get_user().setBackgroundResource(R.drawable.btn_borderless);
        _nav.get_equipes().setBackgroundResource(R.drawable.btn_borderless);
        _nav.get_matchs().setBackgroundResource(R.drawable.btn_borderless);
        _nav.get_paris().setBackgroundResource(R.drawable.btn_borderless);
    }


}