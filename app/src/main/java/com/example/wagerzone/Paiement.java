package com.example.wagerzone;

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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.paymentsheet.PaymentSheet;
import com.stripe.android.paymentsheet.PaymentSheetResult;
import com.stripe.android.paymentsheet.PaymentSheetResultCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Paiement extends AppCompatActivity {
    private TextView editMontant;
    private float multiplicateur = 1;
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
        editMontant = findViewById(R.id.montant);
        if(getIntent().hasExtra("retrait"))
        {
            multiplicateur = -1;
            TextView titre = findViewById(R.id.titreTransactions);
            titre.setText("Retrait : ");
        }
        //Bouton retour
        Button btnRetour = findViewById(R.id.btnRetour);
        btnRetour.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
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
                    montant = montant * multiplicateur;
                    Intent intent = new Intent(Paiement.this, ExecutionTransaction.class);
                    intent.putExtra("montant", montant);
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(Paiement.this, "Le montant minimum est 5$", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}