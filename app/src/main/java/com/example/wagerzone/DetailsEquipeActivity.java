package com.example.wagerzone;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DetailsEquipeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_details_equipe);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageView logoImageView = findViewById(R.id.logoImageView);
        TextView nomEquipeTextView = findViewById(R.id.nomEquipeTextView);
        TextView entraineur = findViewById(R.id.entraineur);
        TextView stade = findViewById(R.id.stade);
        TextView matchJoueTextView = findViewById(R.id.matchsJoues);
        TextView victoireTextView = findViewById(R.id.victoires);
        TextView defaiteTextView = findViewById(R.id.defaites);
        TextView matchNulTextView = findViewById(R.id.matchsNuls);
        TextView defaiteProlongationTextView = findViewById(R.id.defaitesProlongation);

        // Récupérer les données passées depuis l'activité précédente
        int imageId = getIntent().getIntExtra("imageId", 0);
        String nomEquipe = getIntent().getStringExtra("nomEquipe");
        String entraineurEquipe = getIntent().getStringExtra("entraineurEquipe");
        String stadeEquipe = getIntent().getStringExtra("stadeEquipe");
        String matchJoue = getIntent().getStringExtra("matchJoue");
        String victoire = getIntent().getStringExtra("victoire");
        String defaite = getIntent().getStringExtra("defaite");
        String matchNul = getIntent().getStringExtra("matchNul");
        String defaiteProlongation = getIntent().getStringExtra("defaiteProlongation");

        // Définir le nom de l'équipe
        nomEquipeTextView.setText(nomEquipe);
        entraineur.setText(entraineurEquipe);
        stade.setText(stadeEquipe);
        matchJoueTextView.setText(matchJoue);
        victoireTextView.setText(victoire);
        defaiteTextView.setText(defaite);
        matchNulTextView.setText(matchNul);
        defaiteProlongationTextView.setText(defaiteProlongation);

        // Définir le logo de l'équipe
        if (imageId != 0) {
            logoImageView.setImageResource(imageId);
        }
        //Bouton retour
        Button btnRetour = findViewById(R.id.btnRetour);
        btnRetour.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                finish();
            }
        });
    }
}