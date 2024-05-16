package com.example.wagerzone;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ConnexionActivity extends AppCompatActivity  implements View.OnClickListener {

    private Button home,equipes,matchs,paris;
    private ImageButton user;

    private boolean selected =false;
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
        if (v.getId() == R.id.userIcone){
            showUserMenu(v);
        }
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
    private void showUserMenu(View view) {
        PopupMenu menuUser = new PopupMenu(this,view);
        menuUser.getMenuInflater().inflate(R.menu.user_menu,menuUser.getMenu());
        user.setBackgroundResource(R.drawable.rounded_corner);

        menuUser.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.connecter) {
                    Intent connexionIntent = new Intent(ConnexionActivity.this,ConnexionActivity.class);
                    //Avant de lancer l'activité
                    home.setBackgroundResource(R.drawable.btn_borderless);
                    selected = true;
                    startActivity(connexionIntent);
                    finish();
                    return true;
                }
                if (item.getItemId() == R.id.deconnecter) {
                    Intent connexionIntent = new Intent(ConnexionActivity.this,ConnexionActivity.class);
                    //Avant de lancer l'activité
                    home.setBackgroundResource(R.drawable.btn_borderless);
                    selected = true;
                    startActivity(connexionIntent);
                    finish();
                    return true;
                }
                if (item.getItemId() == R.id.inscription) {
                    Intent connexionIntent = new Intent(ConnexionActivity.this,ConnexionActivity.class);
                    //Avant de lancer l'activité
                    home.setBackgroundResource(R.drawable.btn_borderless);
                    selected = true;
                    startActivity(connexionIntent);
                    finish();
                    return true;
                }
                if (item.getItemId() == R.id.compte) {
                    Intent connexionIntent = new Intent(ConnexionActivity.this,ConnexionActivity.class);
                    //Avant de lancer l'activité
                    home.setBackgroundResource(R.drawable.btn_borderless);
                    selected = true;
                    startActivity(connexionIntent);
                    finish();
                    return true;
                }
                if (item.getItemId() == R.id.portefeuille) {
                    Intent connexionIntent = new Intent(ConnexionActivity.this,ConnexionActivity.class);
                    //Avant de lancer l'activité
                    home.setBackgroundResource(R.drawable.btn_borderless);
                    selected = true;
                    startActivity(connexionIntent);
                    finish();
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