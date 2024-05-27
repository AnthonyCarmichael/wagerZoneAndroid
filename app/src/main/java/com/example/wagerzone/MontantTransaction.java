package com.example.wagerzone;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.math.BigDecimal;
/**
 * Activité pour effectuer une transaction de montant, soit un dépôt, soit un retrait.
 */
public class MontantTransaction extends AppCompatActivity {
    private TextView editMontant;
    private boolean estRetrait;
    private Utilisateur user;
    private TextView fondsView;
    /**
     * Méthode appelée lors de la création de l'activité.
     * Initialise les vues, les variables et configure les écouteurs de clics pour les boutons.
     * @param savedInstanceState État de l'activité sauvegardé.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_paiement);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        fondsView = findViewById(R.id.fondsView);
        fondsView.setText("Fonds : " + getIntent().getStringExtra("fonds"));
        user = (Utilisateur) getIntent().getSerializableExtra("user");
        editMontant = findViewById(R.id.montant);
        estRetrait = getIntent().hasExtra("retrait");
        if(estRetrait)
        {
            TextView titre = findViewById(R.id.titreTransactions);
            titre.setText("Retrait : ");
        }
        //Bouton retour
        Button btnRetour = findViewById(R.id.btnRetour);
        btnRetour.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent retour = new Intent();
                retour.putExtra("user", user);
                setResult(Activity.RESULT_CANCELED, retour);
                finish();
            }
        });

        Button btnPaiement = findViewById(R.id.btnPaiement);
        btnPaiement.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                float montant = 0;
                if(!editMontant.getText().toString().trim().equals(""))
                    montant = Float.valueOf(editMontant.getText().toString().trim());
                if(montant >= 5)
                {
                    BigDecimal bigMontant;
                    Intent intent = new Intent(MontantTransaction.this, ExecutionTransaction.class);
                    if(estRetrait && user.get_fonds().floatValue() > montant){
                        intent.putExtra("montant", montant);
                        intent.putExtra("user", user);
                        intent.putExtra("retrait", true);
                        startActivityForResult(intent, 0);
                    }
                    else if (!estRetrait) {
                        intent.putExtra("montant", montant);
                        intent.putExtra("user", user);
                        startActivityForResult(intent, 0);
                    }
                    else {
                        Toast.makeText(MontantTransaction.this, "Le montant doit etre moindre que vois fonds", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(MontantTransaction.this, "Le montant minimum est 5$", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    /**
     * Méthode appelée lors du retour d'une activité lancée avec startActivityForResult.
     * Met à jour les données de l'utilisateur si la transaction a été effectuée avec succès.
     * @param requestCode Le code de requête passé à startActivityForResult.
     * @param resultCode Le code de résultat renvoyé par l'activité enfant.
     * @param data L'intent contenant les données retournées.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                if(!data.hasExtra("canceled"))
                {
                    Intent retour = new Intent();
                    retour.putExtra("user", user);
                    setResult(Activity.RESULT_OK, retour);
                    finish();
                }
            }
        }
    }
}