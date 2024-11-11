package com.example.assign3;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import androidx.recyclerview.widget.RecyclerView;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientManager {

    private final String token;
    private List<Client> clientListOriginal;
    private List<Client> clientList;
    private ClientAdapter adapter;
    private Context context;
    private final ExecutorService executorService;

    public ClientManager(Context context, RecyclerView recyclerView, String token) {
        this.context = context;
        this.token = token;
        clientListOriginal = new ArrayList<>();
        clientList = new ArrayList<>();
        adapter = new ClientAdapter(clientList, context);
        recyclerView.setAdapter(adapter);

        // Set up a thread pool for background tasks
        executorService = Executors.newSingleThreadExecutor();

        // Start asynchronous data fetching
        fetchClients();
    }

    // Method to fetch clients asynchronously using CompletableFuture and ExecutorService
    public void fetchClients() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            CompletableFuture.supplyAsync(() -> {
                List<Client> fetchedClients = new ArrayList<>();
                try {
                    URL url = new URL("http://10.0.2.2:5000/clients");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.addRequestProperty("Authorization", token);

                    int responseCode = conn.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        StringBuilder response = new StringBuilder();
                        String inputLine;

                        while ((inputLine = in.readLine()) != null) {
                            response.append(inputLine);
                        }
                        in.close();

                        // Parse the JSON response
                        JSONArray jsonArray = new JSONArray(response.toString());
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            // Extract fields with checks
                            int id = jsonObject.has("id") ? jsonObject.getInt("id") : -1;
                            String firstName = jsonObject.optString("first_name", "N/A");
                            String lastName = jsonObject.optString("last_name", "N/A");
                            String address = jsonObject.optString("address", "N/A");

                            // Create a Client object and add it to the list
                            fetchedClients.add(new Client(R.drawable.person_icon_placeholder,  firstName, lastName, address,id));
                        }
                    }
                } catch (Exception e) {
                    Log.e("ClientManager", "Exception during fetching clients", e);
                }
                return fetchedClients; // Return the fetched clients list
            }).thenAccept(fetchedClients -> {
                // Run this on the main thread to update the UI
                ((Activity) context).runOnUiThread(() -> {
                    // Update client list and notify adapter
                    clientList.clear();
                    clientList.addAll(fetchedClients);
                    clientListOriginal.addAll(fetchedClients);
                    adapter.notifyDataSetChanged();

                });
            });
        }
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
            // Otherwise, filter the list based on the search text
            List<Client> filteredList = new ArrayList<>();
            for (Client client : clientListOriginal) {
                if (client.getFirstName().toLowerCase().contains(text.toLowerCase()) ||
                        client.getLastName().toLowerCase().contains(text.toLowerCase()) ||
                        client.getAddress().toLowerCase().contains(text.toLowerCase())) {
                    filteredList.add(client);
                }
            }
            clientList.clear();
            clientList.addAll(filteredList);
        }
        adapter.notifyDataSetChanged();
    }

    public void sortClients(int position) {
        Comparator<Client> comparator;
        switch (position) {
            case 0: // Sort by first name
                comparator = Comparator.comparing(Client::getFirstName);
                break;
            case 1: // Sort by last name
                comparator = Comparator.comparing(Client::getLastName);
                break;
            case 2: // Sort by address
                comparator = Comparator.comparing(Client::getAddress);
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
