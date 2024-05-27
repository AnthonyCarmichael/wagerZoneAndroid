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
    private float totalMontantParis;
    private BigDecimal fonds = BigDecimal.valueOf(0);
    private static final DecimalFormat decfor = new DecimalFormat("0.00");
    /**
     * @author Jean-Loup Dandurand-Pominville
     * @version 1.0
     * Méthode appelée à création de l'activité.
     * Initialise les vues, les variables et configure les écouteurs de clics pour les boutons.
     * @param savedInstanceState État de l'activité sauvegardé.
     */
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
        //Déclare les variables globales
        user = (Utilisateur) getIntent().getSerializableExtra("user");
        db = new SQLiteManager(Portefeuille.this);
        //Déclare les paris et le montant total
        paris = db.getParisActifs();
        if(paris != null){
            for (Paris pari:paris) {
                totalMontantParis += pari.get_montant();
            }
        }
        //nav = new Nav(this, findViewById(android.R.id.content),Portefeuille.this);
        //Déclare les TextView des montants
        montantTotalView = findViewById(R.id.montantTotal);
        montantParisView = findViewById(R.id.montantParis);
        montantRetirable = findViewById(R.id.montantRetirable);
        //Affecte le text des montant
        chargeMontantRetirable();
        chargeMontanParis();
        chargeMontantTotal();
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
    /**
     * @author Jean-Loup Dandurand-Pominville
     * @version 1.0
     * Méthode appelée lors d'un clic sur une vue.
     * Gère les actions en fonction du bouton cliqué.
     * @param v La vue qui a été cliquée.
     */
    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.buttonAjoutFonds){
            Intent intentAjoutFonds = new Intent(Portefeuille.this, MontantTransaction.class);
            //Ajoute les informations dans l'intent
            intentAjoutFonds.putExtra("user", user);
            intentAjoutFonds.putExtra("fonds", fonds.toString());
            startActivityForResult(intentAjoutFonds, 0);
        }
        if(v.getId()==R.id.buttonTransactions){
            Intent intentTransactions = new Intent(Portefeuille.this, MesTransactions.class);
            //Ajoute les informations dans l'intent
            intentTransactions.putExtra("user", user);
            startActivity(intentTransactions);
        }
        if(v.getId()==R.id.buttonRetirFonds){
            Intent intentRetirFonds = new Intent(Portefeuille.this, MontantTransaction.class);
            //Ajoute les informations dans l'intent
            intentRetirFonds.putExtra("retrait", -1);
            intentRetirFonds.putExtra("user", user);
            intentRetirFonds.putExtra("fonds", fonds.toString());
            startActivityForResult(intentRetirFonds, 0);
        }
        if(v.getId()==R.id.btnRetour){
            finish();
        }
    }
    /**
     * @author Jean-Loup Dandurand-Pominville
     * @version 1.0
     * Méthode appelée lors du retour d'une activité lancée avec startActivityForResult.
     * Met à jour les données de l'utilisateur et les montants affichés.
     * @param requestCode Le code de requête passé à startActivityForResult.
     * @param resultCode Le code de résultat renvoyé par l'activité enfant.
     * @param data L'intent contenant les données retournées.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Au retour, affecte les données de l'utilisateur
        user = (Utilisateur) data.getSerializableExtra("user");
        //Affecte les données de la page
        chargeMontantRetirable();
        chargeMontantTotal();
    }
    /**
     * @author Jean-Loup Dandurand-Pominville
     * @version 1.0
     * Charge et affiche le montant retirable à partir des fonds gérés par GestionFonds.
     */
    private void chargeMontantRetirable(){
        GestionFonds gestionFonds = new GestionFonds();
        fonds = gestionFonds.getFonds().setScale(2, RoundingMode.HALF_EVEN);
        montantRetirable.setText(fonds.toString() + '$');
    }
    /**
     * @author Jean-Loup Dandurand-Pominville
     * @version 1.0
     * Charge et affiche le montant total des paris.
     */
    private void chargeMontanParis(){
        montantParisView.setText(decfor.format(totalMontantParis) + '$');
    }
    /**
     * @author Jean-Loup Dandurand-Pominville
     * @version 1.0
     * Calcule et affiche le montant total combinant les fonds et le montant des paris.
     */
    private void chargeMontantTotal(){
        BigDecimal total = fonds.add(BigDecimal.valueOf(totalMontantParis)).setScale(2, RoundingMode.HALF_EVEN);
        montantTotalView.setText(total.toString() + '$');
    }


}
