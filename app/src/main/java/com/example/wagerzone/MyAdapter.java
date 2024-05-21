package com.example.wagerzone;

import android.content.Context;
import android.provider.Telephony;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private final RecyclerViewInterface recyclerViewInterface;
    private ArrayList<Paris> paris;
    private Context context;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    public MyAdapter(Context context, ArrayList<Paris> paris, RecyclerViewInterface recyclerViewInterface){
        this.context = context;
        this.paris = paris;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Partie partie = new Partie();
        Equipe visiteur = new Equipe();
        Equipe receveur = new Equipe();
        Paris pari = new Paris();
        pari = paris.get(position);
        SQLiteManager sqLiteManager = new SQLiteManager(context);
        try {
            partie = sqLiteManager.getPartie(pari.get_partie());
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

    @Override
    public int getItemCount() {
        return paris.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView match;
        TextView mise;
        public MyViewHolder(View itemView){
            super(itemView);
            mise = (TextView) itemView.findViewById(R.id.mise);
            match = (TextView) itemView.findViewById(R.id.match);

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

