package com.example.assign3;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ClientManager {

    private List<Client> clientListOriginal; // Original list of clients
    private List<Client> clientList; // Current display list of clients
    private ClientAdapter adapter;

    public ClientManager(Context context, RecyclerView recyclerView) {
        // Initialize the original client list
        clientListOriginal = new ArrayList<>();
        clientListOriginal.add(new Client(R.drawable.person_icon_placeholder, 1, "John Doe", "completed", "123 Main St, Springfield"));
        clientListOriginal.add(new Client(R.drawable.person_icon_placeholder, 2, "Jane Smith", "refused", "456 Oak Ave, Metropolis"));
        clientListOriginal.add(new Client(R.drawable.person_icon_placeholder, 3, "Emily Johnson", "partial", "789 Pine Rd, Gotham"));
        clientListOriginal.add(new Client(R.drawable.person_icon_placeholder, 4, "Michael Brown", "completed", "321 Elm St, Star City"));
        clientListOriginal.add(new Client(R.drawable.person_icon_placeholder, 5, "Sarah Davis", "refused", "654 Maple St, Central City"));
        // Add more clients as needed...

        // Copy the original list to the display list
        clientList = new ArrayList<>(clientListOriginal);

        adapter = new ClientAdapter(clientList, context);
        recyclerView.setAdapter(adapter);
    }

    public ClientAdapter getAdapter() {
        return adapter;
    }

    public void filterClients(String text) {
        if (text == null || text.isEmpty()) {
            // Restore the original list if the search text is empty
            clientList.clear();
            clientList.addAll(clientListOriginal);
        } else {
            // Filter the list based on the search text
            clientList.clear();
            for (Client client : clientListOriginal) {
                if (client.getFirstName().toLowerCase().contains(text.toLowerCase()) ||
                        client.getLastName().toLowerCase().contains(text.toLowerCase()) ||
                        client.getAddress().toLowerCase().contains(text.toLowerCase())) {
                    clientList.add(client);
                }
            }
            System.out.println(clientList);
        }
        adapter.notifyDataSetChanged();
    }

    public void sortClients(int position) {
        Comparator<Client> comparator;

        switch (position) {
            case 0: // Sort by first name
                comparator = Comparator.comparing(Client::getFirstName, Comparator.nullsLast(String::compareTo));
                break;
            case 1: // Sort by last name
                comparator = Comparator.comparing(Client::getLastName, Comparator.nullsLast(String::compareTo));
                break;
            case 2: // Sort by address
                comparator = Comparator.comparing(Client::getAddress, Comparator.nullsLast(String::compareTo));
                break;
            default:
                return;
        }


        Collections.sort(clientList, comparator);
        adapter.notifyDataSetChanged();
    }

    public List<Client> getClients() {
        return clientList;
    }
}
