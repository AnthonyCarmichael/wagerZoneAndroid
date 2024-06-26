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

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
/**
 *
 * @author Maxime Malette
 * @version 1.0
 *
 * Cette activité permet à l'utilisateur de modifier un de ses paris
 */
public class ModificationParisActivity extends AppCompatActivity {
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

        setContentView(R.layout.activity_modification_paris);
        this._nav = new Nav(this,findViewById(android.R.id.content),ModificationParisActivity.this);
        TextView titre = findViewById(R.id.titre);
        TextView match = findViewById(R.id.match2);
        TextView nomVisiteur = findViewById(R.id.nomVisiteur);
        TextView nomReceveur = findViewById(R.id.nomReceveur);
        TextView coteVisiteur = findViewById(R.id.coteVisiteur);
        TextView coteReceveur = findViewById(R.id.coteReceveur);
        TextView miseReceveur = findViewById(R.id.miseReceveur);
        TextView miseVisiteur = findViewById(R.id.miseVisiteur);
        ImageView logoVisiteur = findViewById(R.id.logoVisiteur);
        ImageView logoReceveur = findViewById(R.id.logoReceveur);
        Button retour = findViewById(R.id.retour);
        Button accepter = findViewById(R.id.accepter);
        titre.setText("Modification paris");
        Intent intent = getIntent();
        float montantPrec = intent.getFloatExtra("montant", 0);
        match.setText("Match : " + intent.getStringExtra("nomVisiteur") + " vs " + intent.getStringExtra("nomReceveur"));
        nomVisiteur.setText(intent.getStringExtra("nomVisiteur"));
        nomReceveur.setText(intent.getStringExtra("nomReceveur"));
        coteVisiteur.setText("Cote : " + String.valueOf(intent.getIntExtra("coteVisiteur", 0)));
        coteReceveur.setText("Cote : " + String.valueOf(intent.getIntExtra("coteReceveur", 0)));
        if(intent.getIntExtra("equipeMise", 2) == 0){
            miseVisiteur.setText(String.valueOf(intent.getFloatExtra("montant", 0)));
        }
        if(intent.getIntExtra("equipeMise", 2) == 1){
            miseReceveur.setText(String.valueOf(intent.getFloatExtra("montant", 0)));
        }

        String uriVisiteur = "@drawable/"+(intent.getStringExtra("nomVisiteur")).replaceAll(" ", "_").toLowerCase();
        int imageResourceVisiteur = getResources().getIdentifier(uriVisiteur, null, getPackageName());
        Drawable logoDrawableVisiteur = getResources().getDrawable(imageResourceVisiteur);
        logoVisiteur.setImageDrawable(logoDrawableVisiteur);

        String uriReceveur = "@drawable/"+(intent.getStringExtra("nomReceveur")).replaceAll(" ", "_").toLowerCase();
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

                Intent intent2 = new Intent(ModificationParisActivity.this, ParisActivity.class);
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
                SQLiteManager sqLiteManager = new SQLiteManager(ModificationParisActivity.this);
                AlertDialog.Builder builder = new AlertDialog.Builder(ModificationParisActivity.this);
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
                    builder.setMessage("Le paris à bien été modifier");
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
                                sqLiteManager.modifierParis(intent.getIntExtra("id_paris", 0), Float.parseFloat(miseReceveur.getText().toString()), 1, montantPrec);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            dialog.dismiss();
                            Intent intent3 = new Intent(ModificationParisActivity.this, ParisActivity.class);
                            startActivity(intent3);
                            finish();
                        }
                    });
                }
                else if (miseReceveur.getText().length() == 0 && miseVisiteur.getText().length() != 0) {
                    builder.setTitle(R.string.app_name);
                    builder.setMessage("Le paris à bien été modifier");
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
                                sqLiteManager.modifierParis(intent.getIntExtra("id_paris", 0), Float.parseFloat(miseVisiteur.getText().toString()), 0, montantPrec);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            dialog.dismiss();
                            Intent intent3 = new Intent(ModificationParisActivity.this, ParisActivity.class);
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
    @Override
    protected void onResume() {
        super.onResume();
        _nav.get_home().setBackgroundResource(R.drawable.rounded_dark_red);
        _nav.get_userIcone().setBackgroundResource(R.drawable.rounded_dark_red);
        _nav.get_equipes().setBackgroundResource(R.drawable.rounded_dark_red);
        _nav.get_matchs().setBackgroundResource(R.drawable.rounded_dark_red);
        _nav.get_paris().setBackgroundResource(R.drawable.rounded_red);
    }
}