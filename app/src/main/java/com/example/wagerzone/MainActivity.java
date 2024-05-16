package com.example.wagerzone;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            SQLiteManager sqLiteManager = new SQLiteManager(MainActivity.this);
            ArrayList<String> nomSport = null;
            try {
                nomSport = sqLiteManager.getNomSport();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            TextView texte = (TextView) findViewById(R.id.text);
            texte.setText(nomSport.get(1));
            return insets;
            //TEST PUSH ANTHO
            //Test Richard
            //Test push max
            //Test push JL
        });
    }
}