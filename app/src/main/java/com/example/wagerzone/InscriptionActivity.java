package com.example.wagerzone;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class InscriptionActivity extends AppCompatActivity {

    private Nav _nav;
    private List<Pays> _pays;
    private List<Ville> _villes;
    private ArrayList<String> _nomVilles;
    private ArrayList<String> _nomPays;
    private Spinner _spinnerVille;
    private Spinner _spinnerPays;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_inscription);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Set le nav et récupere le titre
        this._nav = new Nav(this,findViewById(android.R.id.content),InscriptionActivity.this);
        TextView titre = findViewById(R.id.titre);

        // Mise a jour du titre, et surlignement de l'iconeUser
        titre.setText(R.string.inscription);
        ImageButton userIcone = findViewById(R.id.userIcone);
        userIcone.setBackgroundResource(R.drawable.rounded_red);

        // Set les spinner
        set_spinnerPays();
        set_spinnerVille();
    }

    private void fetchVille(int idPays) {
        SQLiteManager sqLiteManager = new SQLiteManager(InscriptionActivity.this);
        _villes = sqLiteManager.fetchAllVillesByPaysId(idPays);
    }

    private void fetchPays() {
        SQLiteManager sqLiteManager = new SQLiteManager(InscriptionActivity.this);
        _pays = sqLiteManager.fetchAllPays();
    }


    private void set_spinnerVille(){
        // Set les spinner:
        fetchVille(1);
        _nomVilles = new ArrayList<>();
        for (Ville ville: _villes) {
            _nomVilles.add(ville.get_nom_ville());
        }
        ArrayAdapter<String> arrayAdapterVilles=new ArrayAdapter<String>(InscriptionActivity.this,
                android.R.layout.simple_spinner_dropdown_item, _nomVilles);

        Spinner spinnerVille= findViewById(R.id.villes);

        spinnerVille.setAdapter(arrayAdapterVilles);
        this._spinnerVille = spinnerVille;
        _spinnerPays.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                _villes.clear();
                _nomVilles.clear();
                fetchVille(_pays.get(position).get_id_pays());
                for (Ville ville: _villes) {
                    _nomVilles.add(ville.get_nom_ville());
                }
                ArrayAdapter<String> arrayAdapterVilles=new ArrayAdapter<String>(InscriptionActivity.this,
                        android.R.layout.simple_spinner_dropdown_item, _nomVilles);

                Spinner spinnerVille= findViewById(R.id.villes);

                spinnerVille.setAdapter(arrayAdapterVilles);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });
    }

    private void set_spinnerPays(){
        // Set les spinner:
        fetchPays();
        _nomPays= new ArrayList<>();
        for (Pays pays: _pays) {
            _nomPays.add(pays.get_nom_pays());
        }
        ArrayAdapter<String> arrayAdapterVilles=new ArrayAdapter<String>(InscriptionActivity.this,
                android.R.layout.simple_spinner_dropdown_item, _nomPays);

        Spinner spinnerPays= findViewById(R.id.pays);

        spinnerPays.setAdapter(arrayAdapterVilles);
        this._spinnerPays = spinnerPays;
    }

}