package com.example.wagerzone;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
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
        holder.nom.setText(paris.get(position).get_string_montant());
    }

    @Override
    public int getItemCount() {
        return paris.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nom;
        TextView groupe;
        public MyViewHolder(View itemView){
            super(itemView);
            nom = (TextView) itemView.findViewById(R.id.nom);
            groupe = (TextView) itemView.findViewById(R.id.groupe);

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

