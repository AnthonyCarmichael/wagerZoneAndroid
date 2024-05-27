/**
 * La classe PartieAdapter est un adaptateur pour afficher une liste de parties dans un RecyclerView.
 * Elle utilise un ViewHolder pour gérer les vues des éléments individuels.
 */
package com.example.wagerzone;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;

/**
 * PartieAdapter est un adaptateur pour afficher une liste de parties dans un RecyclerView.
 */
public class PartieAdapter extends RecyclerView.Adapter<PartieAdapter.MyViewHolder> {
    /**
     * Interface pour gérer les clics sur les éléments du RecyclerView.
     */
    private final RecyclerViewInterface recyclerViewInterface;

    /**
     * Liste des parties à afficher.
     */
    private ArrayList<Partie> parties;

    /**
     * Contexte dans lequel l'adaptateur est utilisé.
     */
    private Context context;

    /**
     * Constructeur de l'adaptateur.
     *
     * @param context Contexte dans lequel l'adaptateur est utilisé.
     * @param partie Liste des parties à afficher.
     * @param recyclerViewInterface Interface pour gérer les clics sur les éléments du RecyclerView.
     */
    public PartieAdapter(Context context, ArrayList<Partie> partie, RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.parties = partie;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    /**
     * Crée un nouveau ViewHolder pour un élément du RecyclerView.
     *
     * @param parent Le ViewGroup parent dans lequel le nouveau View sera ajouté après qu'il soit lié à une position d'adaptateur.
     * @param viewType Le type de vue de la nouvelle vue.
     * @return Un nouveau ViewHolder qui contient une vue pour un élément du RecyclerView.
     */
    @NonNull
    @Override
    public PartieAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.partie_row, parent, false);
        return new PartieAdapter.MyViewHolder(view);
    }

    /**
     * Lie les données d'une partie à un ViewHolder.
     *
     * @param holder Le ViewHolder qui doit être mis à jour pour représenter les contenus de l'élément à la position donnée.
     * @param position La position de l'élément dans l'ensemble de données de l'adaptateur.
     */
    @Override
    public void onBindViewHolder(@NonNull PartieAdapter.MyViewHolder holder, int position) {
        Partie partie = parties.get(position);

        SQLiteManager sqLiteManager = new SQLiteManager(context);

        Equipe equipe1;
        Equipe equipe2;

        try {
            equipe2 = sqLiteManager.getEquipeReceveur(partie.get_id_partie());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            equipe1 = sqLiteManager.getEquipeVisiteur(partie.get_id_partie());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String nom_equipe1 = equipe1.get_nom_equipe().toLowerCase().replace(' ', '_');
        String nom_equipe2 = equipe2.get_nom_equipe().toLowerCase().replace(' ', '_');

        int imageId1 = context.getResources().getIdentifier(nom_equipe1, "drawable", context.getPackageName());
        int imageId2 = context.getResources().getIdentifier(nom_equipe2, "drawable", context.getPackageName());

        holder.nomEquipe1.setText(equipe1.get_nom_equipe());
        holder.nomEquipe2.setText(equipe2.get_nom_equipe());

        holder.imageView1.setImageResource(imageId1);
        holder.imageView2.setImageResource(imageId2);

        Partie finalPartie = partie;
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailsEquipeActivity.class);
                context.startActivity(intent);
            }
        });
    }

    /**
     * Retourne le nombre total d'éléments dans l'ensemble de données détenu par l'adaptateur.
     *
     * @return Le nombre total d'éléments dans cet adaptateur.
     */
    @Override
    public int getItemCount() {
        return parties.size();
    }

    /**
     * Le ViewHolder pour les éléments du RecyclerView.
     */
    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView1;
        ImageView imageView2;
        TextView nomEquipe1;
        TextView nomEquipe2;
        Button button;

        /**
         * Constructeur du ViewHolder.
         *
         * @param itemView La vue de l'élément du RecyclerView.
         */
        public MyViewHolder(View itemView) {
            super(itemView);
            nomEquipe1 = itemView.findViewById(R.id.nomEquipe1);
            nomEquipe2 = itemView.findViewById(R.id.nomEquipe2);
            imageView1 = itemView.findViewById(R.id.imageEquipe1);
            imageView2 = itemView.findViewById(R.id.imageEquipe2);
            button = itemView.findViewById(R.id.buttonParis);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (recyclerViewInterface != null) {
                        int pos = getAdapterPosition();
                        if (pos != RecyclerView.NO_POSITION) {
                            recyclerViewInterface.onItemClick(pos);
                        }
                    }
                }
            });
        }
    }
}
