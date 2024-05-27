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

public class PartieAdapter extends RecyclerView.Adapter<PartieAdapter.MyViewHolder>{
    private final RecyclerViewInterface recyclerViewInterface;
    private ArrayList<Partie> parties;
    private Context context;

    @NonNull
    @Override
    public PartieAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.equipe_row, parent, false);
        return new PartieAdapter.MyViewHolder(view);
    }

    public PartieAdapter(Context context, ArrayList<Partie> partie, RecyclerViewInterface recyclerViewInterface){
        this.context = context;
        this.parties = partie;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @Override
    public void onBindViewHolder(@NonNull PartieAdapter.MyViewHolder holder, int position) {
        Partie partie = new Partie();
        partie = parties.get(position);

        SQLiteManager sqLiteManager = new SQLiteManager(context);

        ArrayList<EquipePartie> equipesParties = sqLiteManager.getEquipesParties(partie.get_id_partie());


        int imageId1 = context.getResources().getIdentifier(equipesParties.get(1).get_equipe().toLowerCase(), "drawable", context.getPackageName());
        int imageId2 = context.getResources().getIdentifier(equipesParties.get(2).get_equipe().toLowerCase(), "drawable", context.getPackageName());

        holder.nomEquipe1.setText(equipesParties.get(1).get_equipe());
        holder.nomEquipe2.setText(equipesParties.get(2).get_equipe());

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

    @Override
    public int getItemCount() {
        return parties.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView1;
        ImageView imageView2;
        TextView nomEquipe1;
        TextView nomEquipe2;
        Button button;
        public MyViewHolder(View itemView){
            super(itemView);
            nomEquipe1 = (TextView) itemView.findViewById(R.id.nomEquipe1);
            nomEquipe2 = (TextView) itemView.findViewById(R.id.nomEquipe2);

            imageView1 = (ImageView) itemView.findViewById(R.id.imageEquipe1);
            imageView2 = (ImageView) itemView.findViewById(R.id.imageEquipe2);

            button = itemView.findViewById(R.id.buttonParis);

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
