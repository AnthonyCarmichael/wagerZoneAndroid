package com.example.wagerzone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paris);
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

        Button button = (Button) findViewById(R.id.retour);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(ParisActivity.this, MainActivity.class);
        startActivity(intent);
    }
}