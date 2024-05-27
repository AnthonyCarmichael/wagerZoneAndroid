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
    private Nav nav;

    @NonNull
    @Override
    public PartieAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.partie_row, parent, false);
        return new PartieAdapter.MyViewHolder(view);
    }

    public PartieAdapter(Context context, Nav nav, ArrayList<Partie> partie, RecyclerViewInterface recyclerViewInterface){
        this.context = context;
        this.parties = partie;
        this.nav = nav;
        this.recyclerViewInterface = recyclerViewInterface;
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

    @Override
    public void onBindViewHolder(@NonNull PartieAdapter.MyViewHolder holder, int position) {
        Partie partie = new Partie();
        partie = parties.get(position);

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
                Intent intent = new Intent(context, AjoutParisActivity.class);
                intent.putExtra("id_partie", finalPartie.get_id_partie());
                intent.putExtra("id_utilisateur", nav.get_user().get_id());
                context.startActivity(intent);
                ((PartieActivity)context).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return parties.size();
    }

}
