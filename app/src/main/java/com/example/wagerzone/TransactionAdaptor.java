package com.example.wagerzone;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TransactionAdaptor extends RecyclerView.Adapter<TransactionAdaptor.MyViewHolder> {
    private String Dates[];
    private String Types[];
    private String Montants[];
    private Context context;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.transaction_row, parent, false);
        return new MyViewHolder(view);
    }

    public TransactionAdaptor(Context context, String dates[],
                              String types[],
                              String montants[]){
        this.context = context;
        Dates = dates;
        int len = Dates.length;
        Types = types;
        Montants = montants;
    }

    @Override
    public  void onBindViewHolder(@NonNull MyViewHolder holder, int position ){
        holder.Dates.setText(Dates[position]);
        holder.Types.setText(Types[position]);
        holder.Montants.setText(Montants[position]);
    }
    @Override
    public int getItemCount(){
        return Dates.length;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView Dates;
        TextView Types;
        TextView Montants;
        public MyViewHolder(View itemView){
            super(itemView);
            Dates = (TextView) itemView.findViewById(R.id.rvDateTransaction);
            Types = (TextView) itemView.findViewById(R.id.rvTypeTransaction);
            Montants = (TextView) itemView.findViewById(R.id.rvMontantTransaction);
        }

    }
}
