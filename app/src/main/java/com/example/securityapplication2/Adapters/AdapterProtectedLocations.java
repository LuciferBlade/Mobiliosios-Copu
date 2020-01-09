package com.example.securityapplication2.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.securityapplication2.R;

import java.util.List;

public class AdapterProtectedLocations extends RecyclerView.Adapter<AdapterProtectedLocations.ViewHolder> {

    private LayoutInflater inflater;

    private List<String> names, statuses, locNames, locAddresses;

    public AdapterProtectedLocations(Context context, List<String> names, List<String> statuses,
                                     List<String> locNames, List<String> locAdresses) {
        this.inflater = LayoutInflater.from(context);
        this.names = names;
        this.statuses = statuses;
        this.locNames = locNames;
        this.locAddresses = locAdresses;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.protected_locations_row_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String name = names.get(position);
        String status = statuses.get(position);
        String locName = locNames.get(position);
        String locAddress = locAddresses.get(position);

        holder.nameField.setText(name);
        holder.statusField.setText(status);
        holder.locationField.setText(locName);
        holder.addressField.setText(locAddress);
    }

    @Override
    public int getItemCount() {
        return statuses.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView nameField, statusField, locationField, addressField;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameField = itemView.findViewById(R.id.name);
            statusField = itemView.findViewById(R.id.status);
            locationField = itemView.findViewById(R.id.location);
            addressField = itemView.findViewById(R.id.adress);
        }
    }
}
