package com.example.wagerzone;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
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

import java.io.IOException;
/**
 *
 * @author Maxime Malette
 * @version 1.0
 *
 * Cette cactivité permet à l'utilisateur d'ajouter un paris.
 */
public class AjoutParisActivity extends AppCompatActivity {
    private Nav _nav;

    /**
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ajout_paris);
        TextView nomVisiteur = findViewById(R.id.nomVisiteur);
        TextView nomReceveur = findViewById(R.id.nomReceveur);
        TextView coteVisiteur = findViewById(R.id.coteVisiteur);
        TextView coteReceveur = findViewById(R.id.coteReceveur);
        TextView miseReceveur = findViewById(R.id.miseReceveur);
        TextView miseVisiteur = findViewById(R.id.miseVisiteur);
        Button retour = findViewById(R.id.retour);
        Button accepter = findViewById(R.id.accepter);

        this._nav = new Nav(this,findViewById(android.R.id.content),AjoutParisActivity.this);
        SQLiteManager sqLiteManager = new SQLiteManager(AjoutParisActivity.this);

        Equipe equipeVisiteur;
        Equipe equipeReceveur;
        Intent intent =getIntent();
        try {
            equipeReceveur = sqLiteManager.getEquipeReceveur(intent.getIntExtra("id_partie", 0));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            equipeVisiteur = sqLiteManager.getEquipeVisiteur(intent.getIntExtra("id_partie", 0));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        nomVisiteur.setText(equipeVisiteur.get_nom_equipe());
        nomReceveur.setText(equipeReceveur.get_nom_equipe());
        try {
            coteReceveur.setText("Cote : " + sqLiteManager.getCoteReceveur(intent.getIntExtra("id_partie", 0)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            coteVisiteur.setText("Cote : " + sqLiteManager.getCoteVisiteur(intent.getIntExtra("id_partie", 0)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ImageView logoVisiteur = findViewById(R.id.logoVisiteur);
        ImageView logoReceveur = findViewById(R.id.logoReceveur);

        String uriVisiteur = "@drawable/"+(equipeVisiteur.get_nom_equipe()).replaceAll(" ", "_").toLowerCase();
        int imageResourceVisiteur = getResources().getIdentifier(uriVisiteur, null, getPackageName());
        Drawable logoDrawableVisiteur = getResources().getDrawable(imageResourceVisiteur);
        logoVisiteur.setImageDrawable(logoDrawableVisiteur);

        String uriReceveur = "@drawable/"+(equipeReceveur.get_nom_equipe()).replaceAll(" ", "_").toLowerCase();
        int imageResourceReceveur = getResources().getIdentifier(uriReceveur, null, getPackageName());
        Drawable logoDrawableReceveur = getResources().getDrawable(imageResourceReceveur);
        logoReceveur.setImageDrawable(logoDrawableReceveur);

        retour.setOnClickListener(new View.OnClickListener() {
            /**
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {

                Intent intent2 = new Intent(AjoutParisActivity.this, PartieActivity.class);
                startActivity(intent2);
                finish();
            }
        });

        accepter.setOnClickListener(new View.OnClickListener() {
            /**
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                SQLiteManager sqLiteManager = new SQLiteManager(AjoutParisActivity.this);
                AlertDialog.Builder builder = new AlertDialog.Builder(AjoutParisActivity.this);
                if (miseReceveur.getText().length() != 0 && miseVisiteur.getText().length() != 0){
                    builder.setTitle(R.string.app_name);
                    builder.setMessage("Veuillez mettre un montant sur une seule équipe");
                    builder.setIcon(R.drawable.ic_launcher_foreground);
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        /**
                         *
                         * @param dialog the dialog that received the click
                         * @param id the button that was clicked (ex.
                         *              {@link DialogInterface#BUTTON_POSITIVE}) or the position
                         *              of the item clicked
                         */
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });
                }
                else if (miseReceveur.getText().length() == 0 && miseVisiteur.getText().length() == 0){
                    builder.setTitle(R.string.app_name);
                    builder.setMessage("Veuillez mettre un montant sur une équipe");
                    builder.setIcon(R.drawable.ic_launcher_foreground);
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        /**
                         *
                         * @param dialog the dialog that received the click
                         * @param id the button that was clicked (ex.
                         *              {@link DialogInterface#BUTTON_POSITIVE}) or the position
                         *              of the item clicked
                         */
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });
                }
                else if (miseReceveur.getText().length() != 0 && miseVisiteur.getText().length() == 0) {
                    builder.setTitle(R.string.app_name);
                    builder.setMessage("Le paris à bien été ajouter");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        /**
                         *
                         * @param dialog the dialog that received the click
                         * @param id the button that was clicked (ex.
                         *              {@link DialogInterface#BUTTON_POSITIVE}) or the position
                         *              of the item clicked
                         */
                        public void onClick(DialogInterface dialog, int id) {
                            try {
                                sqLiteManager.ajouterParis(intent.getIntExtra("id_partie", 0), Float.parseFloat(miseReceveur.getText().toString()), 1, intent.getIntExtra("id_utilisateur", 0));
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            dialog.dismiss();
                            Intent intent3 = new Intent(AjoutParisActivity.this, ParisActivity.class);
                            startActivity(intent3);
                            finish();
                        }
                    });
                }
                else if (miseReceveur.getText().length() == 0 && miseVisiteur.getText().length() != 0) {
                    builder.setTitle(R.string.app_name);
                    builder.setMessage("Le paris à bien été ajouter");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        /**
                         *
                         * @param dialog the dialog that received the click
                         * @param id the button that was clicked (ex.
                         *              {@link DialogInterface#BUTTON_POSITIVE}) or the position
                         *              of the item clicked
                         */
                        public void onClick(DialogInterface dialog, int id) {
                            try {
                                sqLiteManager.ajouterParis(intent.getIntExtra("id_partie", 0), Float.parseFloat(miseVisiteur.getText().toString()), 0, intent.getIntExtra("id_utilisateur", 0));
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            dialog.dismiss();
                            Intent intent3 = new Intent(AjoutParisActivity.this, ParisActivity.class);
                            startActivity(intent3);
                            finish();
                        }
                    });
                }


                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }
    /**
     * Permet de mettre un highlight sur le bon titre du nav.
     */
    protected void onResume() {
        super.onResume();
        _nav.get_home().setBackgroundResource(R.drawable.rounded_dark_red);
        _nav.get_userIcone().setBackgroundResource(R.drawable.rounded_dark_red);
        _nav.get_equipes().setBackgroundResource(R.drawable.rounded_dark_red);
        _nav.get_matchs().setBackgroundResource(R.drawable.rounded_dark_red);
        _nav.get_paris().setBackgroundResource(R.drawable.rounded_red);
    }
}