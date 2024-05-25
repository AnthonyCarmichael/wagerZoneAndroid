package com.example.wagerzone;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class MesTransactions extends AppCompatActivity {

    private Utilisateur user;
    private String dateTransaction[];
    private String montantTransaction[];
    private String typeTransaction[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_mes_transactions);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Button btnRetour = findViewById(R.id.btnRetour);
        btnRetour.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                finish();
            }
        });
        this.user = (Utilisateur) getIntent().getSerializableExtra("user");
        fetchTransactions();
    }

    //Déclaration des transactions
    private void afficherTransactions(){
        ProgressBar progressBar = findViewById(R.id.progressBar2);
        progressBar.setVisibility(View.INVISIBLE);
        //Déclaration du recyclerView et de son adaptateur
        RecyclerView rv = (RecyclerView) findViewById(R.id.rvTransactions);
        TransactionAdaptor adaptor = new TransactionAdaptor(this, dateTransaction,typeTransaction,montantTransaction);
        //Application des données dans le recycleView
        rv.setAdapter(adaptor);
        rv.setLayoutManager(new LinearLayoutManager(this));
    }
    private void fetchTransactions(){
        //Création de la requete
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://10.0.2.2:8000/api/fetchTransactions";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //capture le retour JSON et la met dans la configuration
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray transactions = jsonObject.getJSONArray("transactions");
                            int nbTransactions = transactions.length();
                            dateTransaction = new String[nbTransactions];
                            montantTransaction = new String[nbTransactions];
                            typeTransaction = new String[nbTransactions];
                            for (int i = 0 ; i < nbTransactions ; i++) {
                                JSONObject transaction = transactions.getJSONObject(nbTransactions - 1 - i);
                                dateTransaction[i] = transaction.getString("date");
                                montantTransaction[i] = transaction.getString("montant") + "$";
                                typeTransaction[i] = "Stripe";
                            }
                            afficherTransactions();
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Une erreur c'est produit", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        }){
            @RequiresApi(api = Build.VERSION_CODES.O) //Pour le LocalDateTime
            protected Map<String, String> getParams(){
                Map<String, String> paramV = new HashMap<>();
                paramV.put("authKey", "abc");
                //Ajouter l'id de l'utilisateur
                paramV.put("id_user", String.valueOf(user.get_id()));
                return paramV;
            }
        };
        queue.add(stringRequest);
    }
}