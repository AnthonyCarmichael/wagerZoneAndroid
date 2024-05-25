package com.example.wagerzone;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

        SQLiteManager sqLiteManager = new SQLiteManager(context);

        holder.nomEquipe.setText(equipe.get_nom_equipe());
    }

    @Override
    public int getItemCount() {
        return equipes.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nomEquipe;
        public MyViewHolder(View itemView){
            super(itemView);
            nomEquipe = (TextView) itemView.findViewById(R.id.nomEquipe);

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
