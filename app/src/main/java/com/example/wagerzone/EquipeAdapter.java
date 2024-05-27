/**
 * La classe EquipeAdapter est un adaptateur pour afficher une liste d'équipes dans un RecyclerView.
 * Elle gère l'affichage des informations sur chaque équipe et les interactions avec les éléments de la liste.
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

import java.util.ArrayList;

/**
 * EquipeAdapter est un adaptateur pour afficher une liste d'équipes dans un RecyclerView.
 */
public class EquipeAdapter extends RecyclerView.Adapter<EquipeAdapter.MyViewHolder> {
    /**
     * L'interface pour gérer les interactions avec les éléments du RecyclerView.
     */
    private final RecyclerViewInterface recyclerViewInterface;

    /**
     * La liste des équipes à afficher.
     */
    private ArrayList<Equipe> equipes;

    /**
     * Le contexte dans lequel l'adaptateur est utilisé.
     */
    private Context context;

    /**
     * Constructeur de l'adaptateur.
     *
     * @param context Le contexte dans lequel l'adaptateur est utilisé.
     * @param equipe La liste des équipes à afficher.
     * @param recyclerViewInterface L'interface pour gérer les interactions avec les éléments du RecyclerView.
     */
    public EquipeAdapter(Context context, ArrayList<Equipe> equipe, RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.equipes = equipe;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    /**
     * Crée une nouvelle vue pour un élément du RecyclerView.
     *
     * @param parent Le ViewGroup dans lequel la nouvelle vue sera ajoutée après avoir été liée à une position d'adaptateur.
     * @param viewType Le type de la nouvelle vue.
     * @return Un nouveau MyViewHolder contenant la vue pour un élément du RecyclerView.
     */
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.equipe_row, parent, false);
        return new MyViewHolder(view);
    }

    /**
     * Lie les données d'une équipe à une vue du RecyclerView.
     *
     * @param holder Le MyViewHolder contenant la vue pour un élément du RecyclerView.
     * @param position La position de l'élément dans la liste des équipes.
     */
    @Override
    public void onBindViewHolder(@NonNull EquipeAdapter.MyViewHolder holder, int position) {
        Equipe equipe = equipes.get(position);
        String nom_equipe = equipe.get_nom_equipe().toLowerCase().replace(' ', '_');

        int imageId = context.getResources().getIdentifier(nom_equipe, "drawable", context.getPackageName());

        holder.nomEquipe.setText(equipe.get_nom_equipe());
        holder.imageView.setImageResource(imageId);
        Equipe finalEquipe = equipe;
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailsEquipeActivity.class);

                intent.putExtra("imageId", imageId);
                intent.putExtra("equipe", finalEquipe.get_nom_equipe());
                intent.putExtra("entraineurEquipe", finalEquipe.get_entraineur());
                intent.putExtra("stadeEquipe", finalEquipe.get_stade());
                intent.putExtra("matchJoue", String.valueOf(finalEquipe.get_match_joue()));
                intent.putExtra("victoire", String.valueOf(finalEquipe.get_victoire()));
                intent.putExtra("defaite", String.valueOf(finalEquipe.get_defaite()));
                intent.putExtra("matchNul", String.valueOf(finalEquipe.get_match_nul()));
                intent.putExtra("defaiteProlongation", String.valueOf(finalEquipe.get_defaite_prolongation()));
                context.startActivity(intent);
            }
        });
    }

    /**
     * Renvoie le nombre total d'éléments dans la liste des équipes.
     *
     * @return Le nombre total d'éléments dans la liste des équipes.
     */
    @Override
    public int getItemCount() {
        return equipes.size();
    }

    /**
     * La classe MyViewHolder représente une vue pour un élément du RecyclerView.
     */
    public class MyViewHolder extends RecyclerView.ViewHolder {
        /**
         * L'ImageView pour afficher l'image de l'équipe.
         */
        ImageView imageView;

        /**
         * Le TextView pour afficher le nom de l'équipe.
         */
        TextView nomEquipe;

        /**
         * Le bouton pour afficher les détails de l'équipe.
         */
        Button button;

        /**
         * Constructeur du MyViewHolder.
         *
         * @param itemView La vue pour un élément du RecyclerView.
         */
        public MyViewHolder(View itemView) {
            super(itemView);
            nomEquipe = itemView.findViewById(R.id.nomEquipe);
            imageView = itemView.findViewById(R.id.imageView);
            button = itemView.findViewById(R.id.button);

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
