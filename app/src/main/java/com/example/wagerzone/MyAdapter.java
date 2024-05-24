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
            Button annuler = (Button) itemView.findViewById(R.id.annuler);
            Button modifier = (Button) itemView.findViewById(R.id.modifier);

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
            annuler.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Paris pari = paris.get(getAdapterPosition());
                    SQLiteManager sqLiteManager = new SQLiteManager(context);
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle(R.string.app_name);
                    builder.setMessage("Voulez vous vraiment annuler ce paris?" + pari.get_id_paris());
                    builder.setIcon(R.drawable.ic_launcher_foreground);
                    builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                            try {
                                sqLiteManager.supprimerParis(pari.get_id_paris());
                                Intent intent = new Intent(v.getContext(), ParisActivity.class);
                                startActivity(v.getContext(), intent, null);
                                ((ParisActivity)context).finish();

                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }

                        }
                    });
                    builder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            });
            modifier.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), ModificationParisActivity.class);
                    startActivity(v.getContext(), intent, null);
                    ((ParisActivity)context).finish();
                }
            });
        }
    }
}

