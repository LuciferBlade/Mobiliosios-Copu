package com.example.securityapplication2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterLocation extends RecyclerView.Adapter<AdapterLocation.ViewHolder> {

    LayoutInflater inflater;

    List<String> names, addresses;

    public AdapterLocation (Context context, List<String> names, List<String> addresses){
        this.inflater = LayoutInflater.from(context);
        this.names = names;
        this.addresses = addresses;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.location_row_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String name = names.get(position);
        String address = addresses.get(position);

        holder.nameField.setText(name);
        holder.addressField.setText(address);
    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView nameField, addressField;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameField = itemView.findViewById(R.id.name);
            addressField = itemView.findViewById(R.id.address);
        }
    }
}
