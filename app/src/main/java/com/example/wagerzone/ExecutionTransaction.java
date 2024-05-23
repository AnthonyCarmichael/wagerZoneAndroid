package com.example.wagerzone;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.RequiresApi;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class ExecutionTransaction extends AppCompatActivity {
    private PaymentSheet paymentSheet;
    private String paymentIntentClientSecret;
    private PaymentSheet.CustomerConfiguration configuration;
    private float montant;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_execution_transaction);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        montant = getIntent().getFloatExtra("montant", 0);
        if(montant < 5)
        {
            Toast.makeText(getApplicationContext(), "Minimum de 5$ requis", Toast.LENGTH_SHORT).show();
            finish();
        }
        paymentSheet = new PaymentSheet(this, this::onPaymentSheetResult);
        //Bouton retour
        Button btnRetour = findViewById(R.id.btnRetour);
        btnRetour.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                finish();
            }
        });
        fetchPaiementApi();


    }
    public void fetchPaiementApi() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://10.0.2.2:8000/api/fetchPaiement";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            configuration = new PaymentSheet.CustomerConfiguration(
                                    jsonObject.getString("customer"),
                                    jsonObject.getString("ephemeralKey")
                            );
                            paymentIntentClientSecret = jsonObject.getString("paymentIntent");
                            PaymentConfiguration.init(getApplicationContext(), jsonObject.getString("publishableKey"));
                            ouverturePaiement();
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }){
            protected Map<String, String> getParams(){
                Map<String, String> paramV = new HashMap<>();
                paramV.put("authKey", "abc");
                paramV.put("montant", String.valueOf(montant));
                return paramV;
            }
        };
        queue.add(stringRequest);

    }


    public void ouverturePaiement(){
        if(paymentIntentClientSecret != null)
        {
            paymentSheet.presentWithPaymentIntent(paymentIntentClientSecret,
                    new PaymentSheet.Configuration("Codes Easy", configuration));
        }
        else{
            Toast.makeText(getApplicationContext(), "Erreur du serveur", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void onPaymentSheetResult(final PaymentSheetResult paymentSheetResult) {
        //si l'action est cancellé
        if(paymentSheetResult instanceof PaymentSheetResult.Canceled){
            Toast.makeText(this, "Cancellé", Toast.LENGTH_SHORT).show();
            finish();
        }
        //Si le paiment n'a pas fonctionné
        if(paymentSheetResult instanceof PaymentSheetResult.Failed){
            Toast.makeText(this, ((PaymentSheetResult.Failed) paymentSheetResult).getError().getMessage(), Toast.LENGTH_SHORT).show();
            finish();
        }
        //Si la transaction a réussi
        if(paymentSheetResult instanceof PaymentSheetResult.Completed){
            effectueTransaction();
            Toast.makeText(this, "Paiement effectué", Toast.LENGTH_SHORT).show();
            finish();
        }
        //Autre erreur
        else {
            Toast.makeText(this, "Erreur du PaymentSheetResult", Toast.LENGTH_SHORT).show();
            finish();

        }
    }

    private void effectueTransaction() {
        //Création de la requete
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://10.0.2.2:8000/api/effectuer/transaction";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //capture le retour JSON et la met dans la configuration
                            JSONObject jsonObject = new JSONObject(response);
                            if(jsonObject.has("Succes"))
                                Toast.makeText(ExecutionTransaction.this, "Succes, la transaction c'est bien effectuée", Toast.LENGTH_SHORT).show();
                            else
                                Toast.makeText(ExecutionTransaction.this, "Erreur, une erreur c'est produit durant la transaction, veuillez contacter le support technique", Toast.LENGTH_SHORT).show();
                            finish();
                            //Ouvre la page de paiement Stripe
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }){
            @RequiresApi(api = Build.VERSION_CODES.O) //Pour le LocalDateTime
            protected Map<String, String> getParams(){
                Map<String, String> paramV = new HashMap<>();
                paramV.put("authKey", "abc");
                paramV.put("date", LocalDateTime.now().toString());
                //Ajouter l'id de l'utilisateur
                paramV.put("id_user", "6");
                paramV.put("montant", String.valueOf(montant));
                return paramV;
            }
        };
        queue.add(stringRequest);
    }
}