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

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private LayoutInflater inflater;

    private List<String> names, emails;

    public Adapter(Context context, List<String> names, List<String> emails) {
        this.inflater = LayoutInflater.from(context);
        this.names = names;
        this.emails = emails;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String name = names.get(position);
        String email = emails.get(position);

        holder.nameField.setText(name);
        holder.emailField.setText(email);
    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView nameField, emailField;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameField = itemView.findViewById(R.id.name);
            emailField = itemView.findViewById(R.id.email);
        }
    }
}
