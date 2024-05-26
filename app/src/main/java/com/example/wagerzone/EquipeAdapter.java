package com.example.wagerzone;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;

public class EquipeAdapter extends RecyclerView.Adapter<EquipeAdapter.MyViewHolder>{
    private final RecyclerViewInterface recyclerViewInterface;
    private ArrayList<Equipe> equipes;
    private Context context;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.equipe_row, parent, false);
        return new MyViewHolder(view);
    }

    public EquipeAdapter(Context context, ArrayList<Equipe> equipe, RecyclerViewInterface recyclerViewInterface){
        this.context = context;
        this.equipes = equipe;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @Override
    public void onBindViewHolder(@NonNull EquipeAdapter.MyViewHolder holder, int position) {
        Equipe equipe = new Equipe();
        equipe = equipes.get(position);
        String nom_equipe = equipe.get_nom_equipe().toLowerCase().replace(' ', '_');
        int imageId = context.getResources().getIdentifier(nom_equipe, "drawable", context.getPackageName());

        SQLiteManager sqLiteManager = new SQLiteManager(context);

        holder.nomEquipe.setText(equipe.get_nom_equipe());
        holder.imageView.setImageResource(imageId);
        Equipe finalEquipe = equipe;
        int nb_victoire = finalEquipe.get_victoire();
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailsEquipeActivity.class);

                intent.putExtra("imageId", imageId);
                intent.putExtra("equipe", );
                intent.putExtra("entraineurEquipe", finalEquipe.get_entraineur());
                intent.putExtra("stadeEquipe", finalEquipe.get_stade());
                intent.putExtra("matchJoue", finalEquipe.get_match_joue());
                intent.putExtra("victoire", finalEquipe.get_victoire());
                intent.putExtra("defaite", finalEquipe.get_defaite());
                intent.putExtra("matchNull", finalEquipe.get_match_nul());
                intent.putExtra("defaiteProlongation", finalEquipe.get_defaite_prolongation());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return equipes.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView nomEquipe;
        Button button;
        public MyViewHolder(View itemView){
            super(itemView);
            nomEquipe = (TextView) itemView.findViewById(R.id.nomEquipe);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            button = itemView.findViewById(R.id.button);

            itemView.setOnClickListener(new View.OnClickListener() {
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
        }
    }
}
