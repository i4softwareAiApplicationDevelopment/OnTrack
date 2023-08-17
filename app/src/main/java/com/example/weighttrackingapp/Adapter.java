package com.example.weighttrackingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>{
    private Context context;
    private ArrayList weight_id, date_id;

    // Adapter constructor
    public Adapter(Context context, ArrayList weight_id, ArrayList date_id) {
        this.context = context;
        this.weight_id = weight_id;
        this.date_id = date_id;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.userentry, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Bind weight and date info to card
        holder.weight_id.setText(String.valueOf(weight_id.get(position)));
        holder.date_id.setText(String.valueOf(date_id.get(position)));
    }

    @Override
    public int getItemCount() {
        return weight_id.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView weight_id, date_id;

        // Identify views associated with weight and date
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            weight_id = itemView.findViewById(R.id.textWeight);
            date_id = itemView.findViewById(R.id.textDate);

        }
    }

}
