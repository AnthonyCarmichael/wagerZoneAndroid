package com.example.wagerzone;

import android.app.Activity;
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

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class Portefeuille extends AppCompatActivity implements View.OnClickListener{

    private TextView montantTotalView;
    private TextView montantParisView;
    private TextView montantRetirable;
    private ArrayList<Paris> paris;
    private SQLiteManager db;
    private Utilisateur user;

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
        user = (Utilisateur) getIntent().getSerializableExtra("user");

        //nav = new Nav(this, findViewById(android.R.id.content),Portefeuille.this);
        //Déclare les TextView des montants
        montantTotalView = findViewById(R.id.montantTotal);
        montantParisView = findViewById(R.id.montantParis);
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
            Intent intentAjoutFonds = new Intent(Portefeuille.this, MontantTransaction.class);
            intentAjoutFonds.putExtra("user", user);
            startActivityForResult(intentAjoutFonds, 0);
        }
        if(v.getId()==R.id.buttonTransactions){
            Intent intentTransactions = new Intent(Portefeuille.this, MesTransactions.class);
            intentTransactions.putExtra("user", user);
            startActivity(intentTransactions);
        }
        if(v.getId()==R.id.buttonRetirFonds){
            Intent intentRetirFonds = new Intent(Portefeuille.this, MontantTransaction.class);
            intentRetirFonds.putExtra("retrait", -1);
            intentRetirFonds.putExtra("user", user);
            startActivityForResult(intentRetirFonds, 0);
        }
        if(v.getId()==R.id.btnRetour){
            Intent retour = new Intent();
            retour.putExtra("user", user);
            setResult(Activity.RESULT_OK, retour);
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                user = (Utilisateur) data.getSerializableExtra("user");
                chargeMontantRetirable();
                chargeMontantTotal();
            }
        }
    }

    private void chargeMontantTotal(){
        double total = 0;
        montantTotalView.setText(decfor.format(total) + '$');
    }

    private void chargeMontanParis(){

        double total = 0;
        montantParisView.setText(decfor.format(total) + '$');
    }

    private void chargeMontantRetirable(){
        BigDecimal total = user.get_fonds().setScale(2, RoundingMode.HALF_EVEN);
        montantRetirable.setText(total.toString() + '$');
    }
}
