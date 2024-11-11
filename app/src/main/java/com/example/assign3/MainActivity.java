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

import java.net.HttpURLConnection;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ClientManager clientManager;
    private Spinner sortSpinner;
    private SearchView searchView;
    private String token; // Variable to store the token

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.clientRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        clientManager = new ClientManager(this, recyclerView);

        sortSpinner = findViewById(R.id.sortSpinner);
        searchView = findViewById(R.id.searchView);

        // Check if the user is logged in and if the token is valid
        SharedPreferences sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);
        token = sharedPreferences.getString("token", null);
        long loginTimestamp = sharedPreferences.getLong("loginTimestamp", 0);

        // Calculate if more than one day has passed (24 * 60 * 60 * 1000 milliseconds)
        long oneDayInMillis = 24 * 60 * 60 * 1000;
        long currentTime = System.currentTimeMillis();

        if (!isLoggedIn || TextUtils.isEmpty(token) || (currentTime - loginTimestamp > oneDayInMillis)) {
            // Redirect to LoginActivity if not logged in, token is missing, or token is older than one day
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        // Initialize and set up ClientAdapter with data and token
        List<Client> clientList = clientManager.getClients(); // Assuming clientManager can provide client list
        ClientAdapter clientAdapter = new ClientAdapter(clientList, this);
        recyclerView.setAdapter(clientAdapter);

        // Set up search filtering
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                clientManager.filterClients(newText);
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
                clientManager.sortClients(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    // Example method to add Authorization header (if needed for a network request)
    private void addAuthorizationHeader(HttpURLConnection conn) {
        if (!TextUtils.isEmpty(token)) {
            conn.addRequestProperty("Authorization", token);
        }
    }
}
