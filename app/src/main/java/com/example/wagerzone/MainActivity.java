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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button home,equipes,matchs,paris;
    private ImageButton user;
    private boolean selected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            SQLiteManager sqLiteManager = new SQLiteManager(MainActivity.this);
            ArrayList<String> nomPays = null;
            try {
                nomPays = sqLiteManager.getNomPays();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            TextView titre = (TextView) findViewById(R.id.textView4);
            titre.setText(nomPays.get(0));
            return insets;
            //TEST PUSH ANTHO
            //Test Richard
            //Test push max
            //Test push JL
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
            showUserMenu(v);
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

    private void showUserMenu(View view) {
        PopupMenu menuUser = new PopupMenu(this,view);
        menuUser.getMenuInflater().inflate(R.menu.user_menu,menuUser.getMenu());
        user.setBackgroundResource(R.drawable.rounded_corner);

        menuUser.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.connecter) {
                        Intent connexionIntent = new Intent(MainActivity.this,ConnexionActivity.class);
                        //Avant de lancer l'activité
                        home.setBackgroundResource(R.drawable.btn_borderless);
                        selected = true;
                        startActivity(connexionIntent);
                    return true;
                }
                if (item.getItemId() == R.id.deconnecter) {
                    Intent connexionIntent = new Intent(MainActivity.this,ConnexionActivity.class);
                    //Avant de lancer l'activité
                    home.setBackgroundResource(R.drawable.btn_borderless);
                    selected = true;
                    startActivity(connexionIntent);
                    return true;
                }
                if (item.getItemId() == R.id.inscription) {
                    Intent connexionIntent = new Intent(MainActivity.this,ConnexionActivity.class);
                    //Avant de lancer l'activité
                    home.setBackgroundResource(R.drawable.btn_borderless);
                    selected = true;
                    startActivity(connexionIntent);
                    return true;
                }
                if (item.getItemId() == R.id.compte) {
                    Intent connexionIntent = new Intent(MainActivity.this,ConnexionActivity.class);
                    //Avant de lancer l'activité
                    home.setBackgroundResource(R.drawable.btn_borderless);
                    selected = true;
                    startActivity(connexionIntent);
                    return true;
                }
                if (item.getItemId() == R.id.portefeuille) {
                    Intent connexionIntent = new Intent(MainActivity.this,ConnexionActivity.class);
                    //Avant de lancer l'activité
                    home.setBackgroundResource(R.drawable.btn_borderless);
                    selected = true;
                    startActivity(connexionIntent);
                    return true;
                }
                selected = false;
                return false;
            }
        });
        menuUser.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu menu) {
                // Lors de la fermeture du popmenu
                if (!selected)
                    user.setBackgroundResource(R.drawable.btn_borderless);
                else
                    selected=false;
            }
        });

        menuUser.show();
    }
}