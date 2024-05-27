package com.example.wagerzone;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * L'adaptateur TransactionAdaptor est utilisé pour afficher des transactions dans un RecyclerView.
 */
public class TransactionAdaptor extends RecyclerView.Adapter<TransactionAdaptor.MyViewHolder> {
    private String Dates[];
    private String Types[];
    private String Montants[];
    private Context context;

    /**
     * Constructeur de l'adaptateur TransactionAdaptor.
     *
     * @param context   Contexte de l'application.
     * @param dates     Tableau des dates des transactions.
     * @param types     Tableau des types de transactions.
     * @param montants  Tableau des montants des transactions.
     */
    public TransactionAdaptor(Context context, String dates[],
                              String types[],
                              String montants[]) {
        this.context = context;
        Dates = dates;
        Types = types;
        Montants = montants;
    }

    /**
     * Méthode appelée lorsqu'une nouvelle ligne de RecyclerView doit être créée.
     *
     * @param parent   Le parent où la vue doit être attachée.
     * @param viewType Le type de vue.
     * @return Un ViewHolder qui contient la vue pour une ligne de RecyclerView.
     */
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.transaction_row, parent, false);
        return new MyViewHolder(view);
    }

    /**
     * Méthode appelée pour afficher les données à une position spécifique.
     *
     * @param holder   Le ViewHolder à mettre à jour.
     * @param position La position de l'élément dans la liste.
     */
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.Dates.setText(Dates[position]);
        holder.Types.setText(Types[position]);
        holder.Montants.setText(Montants[position]);
    }

    /**
     * Méthode qui retourne le nombre d'éléments dans la liste.
     *
     * @return Le nombre total d'éléments dans la liste.
     */
    @Override
    public int getItemCount() {
        return Dates.length;
    }

    /**
     * Classe ViewHolder qui représente une seule ligne dans RecyclerView.
     */
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Dates;
        TextView Types;
        TextView Montants;

        public MyViewHolder(View itemView) {
            super(itemView);
            Dates = itemView.findViewById(R.id.rvDateTransaction);
            Types = itemView.findViewById(R.id.rvTypeTransaction);
            Montants = itemView.findViewById(R.id.rvMontantTransaction);
        }
    }
}
