package com.example.wagerzone;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Telephony;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
/**
 *
 * @author Maxime Malette
 * @version 1.0
 *
 * Cette classe permet de gérer la base de données interne et la communication avec l'api du site web.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private final RecyclerViewInterface recyclerViewInterface;
    private ArrayList<Paris> paris;
    private Context context;

    /**
     *
     * @param parent The ViewGroup into which the new View will be added after it is bound to
     *               an adapter position.
     * @param viewType The view type of the new View.
     *
     * @return retourne la vue voulue
     */
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    /**
     *
     * @param context Le contexte de l'activité d'où provient l'appel
     * @param paris Les paris de l'utilisateur connecté
     * @param recyclerViewInterface Notre interface qui permet d'implémenter des recyclerView
     */
    public MyAdapter(Context context, ArrayList<Paris> paris, RecyclerViewInterface recyclerViewInterface){
        this.context = context;
        this.paris = paris;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    /**
     *
     * @param holder The ViewHolder which should be updated to represent the contents of the
     *        item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Partie partie = new Partie();
        Equipe visiteur = new Equipe();
        Equipe receveur = new Equipe();
        Paris pari = new Paris();
        pari = paris.get(position);
        SQLiteManager sqLiteManager = new SQLiteManager(context);
        try {
            partie = sqLiteManager.getPartie(pari.get_id_partie());
            visiteur = sqLiteManager.getEquipeVisiteur(partie.get_id_partie());
            receveur = sqLiteManager.getEquipeReceveur(partie.get_id_partie());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        holder.match.setText("Match : " + visiteur.get_nom_equipe() + " vs " + receveur.get_nom_equipe());
        if (pari.get_receveur() == 0){
            holder.mise.setText("Mise : " + pari.get_montant() + "$ sur " + visiteur.get_nom_equipe());
        } else if (pari.get_receveur() == 1) {
            holder.mise.setText("Mise : " + pari.get_montant() + "$ sur " + receveur.get_nom_equipe());
        }

    }

    /**
     *
     * @return Le nombre de paris de l'utilisateur connecté
     */
    @Override
    public int getItemCount() {
        return paris.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView match;
        TextView mise;

        /**
         *
         * @param itemView La view de l'activité qui l'a appelé.
         */
        public MyViewHolder(View itemView){
            super(itemView);
            mise = (TextView) itemView.findViewById(R.id.mise);
            match = (TextView) itemView.findViewById(R.id.match);
            Button annuler = (Button) itemView.findViewById(R.id.annuler);
            Button modifier = (Button) itemView.findViewById(R.id.modifier);

            itemView.setOnClickListener(new View.OnClickListener() {
                /**
                 *
                 * @param v The view that was clicked.
                 */
                @Override
                public void onClick(View v) {
                    if(recyclerViewInterface != null){
                        int pos = getAdapterPosition();

                        if(pos != RecyclerView.NO_POSITION){
                            recyclerViewInterface.onItemClick(pos);
                        }
                    }
                }
            });
            annuler.setOnClickListener(new View.OnClickListener() {
                /**
                 *
                 * @param v The view that was clicked.
                 */
                @Override
                public void onClick(View v) {
                    Paris pari = paris.get(getAdapterPosition());
                    SQLiteManager sqLiteManager = new SQLiteManager(context);
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle(R.string.app_name);
                    builder.setMessage("Voulez vous vraiment annuler ce paris?");
                    builder.setIcon(R.drawable.ic_launcher_foreground);
                    builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                        /**
                         *
                         * @param dialog the dialog that received the click
                         * @param id the button that was clicked (ex.
                         *              {@link DialogInterface#BUTTON_POSITIVE}) or the position
                         *              of the item clicked
                         */
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                            try {
                                sqLiteManager.supprimerParis(pari.get_id_paris(), pari.get_montant());
                                Intent intent = new Intent(v.getContext(), ParisActivity.class);
                                startActivity(v.getContext(), intent, null);
                                ((ParisActivity)context).finish();

                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }

                        }
                    });
                    builder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
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
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            });
            modifier.setOnClickListener(new View.OnClickListener() {
                /**
                 *
                 * @param v The view that was clicked.
                 */
                @Override
                public void onClick(View v) {

                    Partie partie = new Partie();
                    Equipe visiteur = new Equipe();
                    Equipe receveur = new Equipe();
                    int coteVisiteur = 0;
                    int coteReceveur = 0;
                    Paris pari = new Paris();
                    pari = paris.get(getAdapterPosition());
                    SQLiteManager sqLiteManager = new SQLiteManager(context);
                    try {
                        partie = sqLiteManager.getPartie(pari.get_id_partie());
                        visiteur = sqLiteManager.getEquipeVisiteur(partie.get_id_partie());
                        receveur = sqLiteManager.getEquipeReceveur(partie.get_id_partie());
                        coteVisiteur = sqLiteManager.getCoteVisiteur(partie.get_id_partie());
                        coteReceveur = sqLiteManager.getCoteReceveur(partie.get_id_partie());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    Intent intent = new Intent(v.getContext(), ModificationParisActivity.class);
                    intent.putExtra("montant", pari.get_montant());
                    intent.putExtra("nomVisiteur", visiteur.get_nom_equipe());
                    intent.putExtra("nomReceveur", receveur.get_nom_equipe());
                    intent.putExtra("nomReceveur", receveur.get_nom_equipe());
                    intent.putExtra("coteVisiteur", coteVisiteur);
                    intent.putExtra("coteReceveur", coteReceveur);
                    intent.putExtra("equipeMise", pari.get_receveur());
                    intent.putExtra("id_paris", pari.get_id_paris());
                    startActivity(v.getContext(), intent, null);
                    ((ParisActivity)context).finish();
                }
            });
        }
    }
}

