package com.example.wagerzone;

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

public class MesTransactions extends AppCompatActivity {

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

        //Set les données à mettre dans le recycle view
        String test[] = new String[]{"test1", "test2", "test3", "test4"};
        //Déclaration du recyclerView et de son adaptateur
        RecyclerView rv = (RecyclerView) findViewById(R.id.rvTransactions);
        TransactionAdaptor adaptor = new TransactionAdaptor(this, test,test,test);
        //Application des données dans le recycleView
        rv.setAdapter(adaptor);
        rv.setLayoutManager(new LinearLayoutManager(this));
    }

    //Déclaration des transactions
    private void afficherTransactions(){

        //Set les données à mettre dans le recycle view
        String test[] = new String[]{"test1", "test2", "test3", "test4"};
        //Déclaration du recyclerView et de son adaptateur
        RecyclerView rv = (RecyclerView) findViewById(R.id.rvTransactions);
        TransactionAdaptor adaptor = new TransactionAdaptor(this, test,test,test);
        //Application des données dans le recycleView
        rv.setAdapter(adaptor);
        rv.setLayoutManager(new LinearLayoutManager(this));
    }
}