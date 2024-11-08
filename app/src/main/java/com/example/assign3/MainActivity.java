package com.example.assign3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ClientAdapter adapter;
    private List<Client> clientList;
    private Spinner sortSpinner;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.clientRecyclerView);
        sortSpinner = findViewById(R.id.sortSpinner);
        searchView = findViewById(R.id.searchView);

        // Initialize client list
        clientList = new ArrayList<>();
        clientList.add(new Client(R.drawable.person_icon_placeholder, "Anadi", "Frontend", "Newton, BC"));
        clientList.add(new Client(R.drawable.person_icon_placeholder, "Simar", "Login", "Vancouver, BC"));
        clientList.add(new Client(R.drawable.person_icon_placeholder, "Dhruv", "Backend", "Unknown, Canada"));
        // Add more clients as needed...

        adapter = new ClientAdapter(clientList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Check if the user is logged in
        SharedPreferences sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);

        if (!isLoggedIn) {
            // Redirect to LoginActivity if not logged in
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        // Set up search filtering
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterClients(newText);
                return true;
            }
        });

        // Set up sort options
        ArrayAdapter<CharSequence> sortAdapter = ArrayAdapter.createFromResource(this,
                R.array.sort_options, android.R.layout.simple_spinner_item);
        sortAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortSpinner.setAdapter(sortAdapter);

        sortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sortClients(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void filterClients(String text) {
        List<Client> filteredList = new ArrayList<>();
        for (Client client : clientList) {
            if (client.getFirstName().toLowerCase().contains(text.toLowerCase()) ||
                    client.getLastName().toLowerCase().contains(text.toLowerCase()) ||
                    client.getAddress().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(client);
            }
        }
        adapter = new ClientAdapter(filteredList, this);
        recyclerView.setAdapter(adapter);
    }

    private void sortClients(int position) {
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
}
