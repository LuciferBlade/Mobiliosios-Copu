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

public class AdapterLogs extends RecyclerView.Adapter<AdapterLogs.ViewHolder> {

    private LayoutInflater inflater;

    private List<String> names, dates;

    public AdapterLogs(Context context, List<String> names, List<String> dates) {
        this.inflater = LayoutInflater.from(context);
        this.names = names;
        this.dates = dates;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.logs_row_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String name = names.get(position);
        String date = dates.get(position);

        holder.nameField.setText(name);
        holder.dateField.setText(date);
    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView nameField, dateField;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameField = itemView.findViewById(R.id.name);
            dateField = itemView.findViewById(R.id.date);
        }
    }
}
