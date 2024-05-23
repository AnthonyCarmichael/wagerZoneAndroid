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

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class Portefeuille extends AppCompatActivity implements View.OnClickListener{

    private TextView montantTotal;
    private TextView montantParis;
    private TextView montantRetirable;

    private static final DecimalFormat decfor = new DecimalFormat("0.00");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_portefeuille);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //Déclare les TextView des montants
        montantTotal = findViewById(R.id.montantTotal);
        montantParis = findViewById(R.id.montantParis);
        montantRetirable = findViewById(R.id.montantRetirable);
        //Affecte le text des montant
        chargeMontantTotal();
        chargeMontanParis();
        chargeMontantRetirable();

        //Déclare les bouttons
        Button btnAjouteFonds = findViewById(R.id.buttonAjoutFonds);
        Button btnRetout = findViewById(R.id.btnRetour);
        Button btnTransactions = findViewById(R.id.buttonTransactions);
        Button btnRetrait = findViewById(R.id.buttonRetirFonds);

        //affecte les onClickListener sur les bouttons
        btnAjouteFonds.setOnClickListener(this);
        btnRetout.setOnClickListener(this);
        btnTransactions.setOnClickListener(this);
        btnRetrait.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.buttonAjoutFonds){
            Intent intent = new Intent(Portefeuille.this, Paiement.class);
            startActivity(intent);
        }
        if(v.getId()==R.id.buttonTransactions){
            Intent intent = new Intent(Portefeuille.this, MesTransactions.class);
            startActivity(intent);
        }
        if(v.getId()==R.id.buttonRetirFonds){
            Intent intent = new Intent(Portefeuille.this, Paiement.class);
            intent.putExtra("retrait", -1);
            startActivity(intent);
        }
        if(v.getId()==R.id.btnRetour){
            finish();
        }
    }

    private void chargeMontantTotal(){
        double total = 0;
        
        montantTotal.setText(decfor.format(total) + '$');
    }

    private void chargeMontanParis(){
        double total = 0;

        montantParis.setText(decfor.format(total) + '$');
    }

    private void chargeMontantRetirable(){
        double total = 0;

        montantRetirable.setText(decfor.format(total) + '$');
    }
}
