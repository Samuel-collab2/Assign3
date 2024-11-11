package com.example.assign3;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ClientAdapter extends RecyclerView.Adapter<ClientAdapter.ClientViewHolder> {

    private List<Client> clientList;
    private Context context;

    public ClientAdapter(List<Client> clientList, Context context) {
        this.clientList = clientList;
        this.context = context;
    }

    @NonNull
    @Override
    public ClientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.client_item, parent, false);
        return new ClientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClientViewHolder holder, int position) {
        Client client = clientList.get(position);
        holder.photo.setImageResource(client.getPhoto());
        holder.name.setText(client.getFirstName() + " " + client.getLastName());
        holder.address.setText(client.getAddress());

        holder.statusButton.setOnClickListener(v -> {
            boolean visible = holder.statusCheckboxes.getVisibility() == View.VISIBLE;
            holder.statusCheckboxes.setVisibility(visible ? View.GONE : View.VISIBLE);
        });

        // Populate checkboxes with status options
        holder.checkbox1.setText(client.getStatusOptions().get(0));
        holder.checkbox2.setText(client.getStatusOptions().get(1));
        holder.checkbox3.setText(client.getStatusOptions().get(2));

        // Set OnClickListener to navigate to DetailActivity on item click
        holder.itemView.setOnClickListener(v -> {
            // Retrieve the auth token from SharedPreferences
            SharedPreferences sharedPreferences = context.getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE);
            String authToken = sharedPreferences.getString("token", null);

            // Start DetailActivity with clientId and authTok as extras
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("clientId", client.getId()); // Assuming client.getId() returns the client ID
            intent.putExtra("authTok", authToken);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return clientList.size();
    }

    public static class ClientViewHolder extends RecyclerView.ViewHolder {
        ImageView photo;
        TextView name, address;
        Button statusButton;
        LinearLayout statusCheckboxes;
        CheckBox checkbox1, checkbox2, checkbox3;

        public ClientViewHolder(@NonNull View itemView) {
            super(itemView);
            photo = itemView.findViewById(R.id.clientPhoto);
            name = itemView.findViewById(R.id.clientName);
            address = itemView.findViewById(R.id.clientAddress);
            statusButton = itemView.findViewById(R.id.statusButton);
            statusCheckboxes = itemView.findViewById(R.id.statusCheckboxes);
            checkbox1 = itemView.findViewById(R.id.statusCheckbox1);
            checkbox2 = itemView.findViewById(R.id.statusCheckbox2);
            checkbox3 = itemView.findViewById(R.id.statusCheckbox3);
        }
    }

    // Add this method to the existing ClientAdapter class
    public void updateList(List<Client> newList) {
        clientList.clear();
        clientList.addAll(newList);
        notifyDataSetChanged();
    }
}