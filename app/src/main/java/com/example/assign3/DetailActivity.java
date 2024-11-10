package com.example.assign3;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.example.assign3.DetailActivityRes.DataCache;
import com.example.assign3.DetailActivityRes.ViewPagerAdapter;
import com.example.assign3.DetailActivityRes.ViewPagerItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;

import android.util.Base64;


public class DetailActivity extends AppCompatActivity {

    DataCache mDataCache;
    ViewPager2 viewPager2;
    ArrayList<ViewPagerItem> viewPagerItemArrayList;

    int placeholderImage = R.drawable.pika;

    String domain = "http://10.0.2.2:5000";
    String getURL = "http://10.0.2.2:5000/clients";
    String authTok = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VybmFtZSI6ImpvaG5fZG9lIiwiZXhwIjoxNzMxMzU4NjIyfQ.amdQH5UPSY9PXmFBdp36JaiyAfDiU0PH19axePevtlc";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        // Only upon initially creating DetailActivity
        if (savedInstanceState == null) {
            viewPager2 = findViewById(R.id.viewPager);
            setContentView(R.layout.activity_detail);
            mDataCache = new DataCache();
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Get client ID from display page; will be first client shown on detail activity
        int clientId = getIntent().getIntExtra("clientId", 2);

        // Retrieve data asynchronously
        CompletableFuture<ArrayList<ViewPagerItem>> futureData = getClients(clientId);

        // Update UI after data is retrieved
        futureData.thenAccept(viewPagerItems -> {
            runOnUiThread(() -> {
                // Check if data is valid
                viewPager2 = findViewById(R.id.viewPager);
                if (viewPagerItems != null && !viewPagerItems.isEmpty()) {
                    // Initialize the ViewPager adapter only once data is ready
                    if (viewPager2.getAdapter() == null) {
                        viewPagerItemArrayList = new ArrayList<>(viewPagerItems);
                        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(viewPagerItemArrayList, DetailActivity.this, authTok);
                        viewPager2.setAdapter(viewPagerAdapter);
                        viewPager2.setClipToPadding(false);
                        viewPager2.setClipChildren(false);
                        viewPager2.setOffscreenPageLimit(2);
                        viewPager2.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);
                    } else {
                        // If adapter is already set, notify about data change
                        viewPager2.getAdapter().notifyDataSetChanged();
                    }
                } else {
                    Toast.makeText(DetailActivity.this, "No data available", Toast.LENGTH_SHORT).show();
                }
            });
        }).exceptionally(throwable -> {
            // Handle exceptions if any
            runOnUiThread(() -> Toast.makeText(DetailActivity.this, "Error connecting to server", Toast.LENGTH_SHORT).show());
            return null;
        });
    }

    private CompletableFuture<ArrayList<ViewPagerItem>> getClients(int clientId) {
        return CompletableFuture.supplyAsync(() -> {
            ArrayList<ViewPagerItem> items = new ArrayList<>();
            try {
                // Make HTTP request
                URL url = new URL(domain + "/clientsDetail");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.addRequestProperty("Authorization", authTok);
                conn.connect();

                int responseCode = conn.getResponseCode();
                if (responseCode != 200) {
                    throw new RuntimeException("HttpResponse: " + responseCode);
                }

                // Get and process the response
                InputStream response = conn.getInputStream();
                Scanner scanner = new Scanner(response);
                String responseBody = scanner.useDelimiter("\\A").next();
                scanner.close();

                JSONArray jsonarray = new JSONArray(responseBody);
                items = processJsonResponse(jsonarray, clientId);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return items;
        });
    }

    private ArrayList<ViewPagerItem> processJsonResponse(JSONArray jsonarray, int clientId) {
        ArrayList<ViewPagerItem> items = new ArrayList<>();
        try {
            for (int i = 0; i < jsonarray.length(); i++) {
                JSONObject jsonobject = jsonarray.getJSONObject(i);
                JSONObject jsonObjectDetails = jsonobject.getJSONObject("client_details");

                Bitmap bmp;
                int client_id = jsonobject.getInt("client_id");
                if(mDataCache.isCached(client_id)) {
                    System.out.println("***");
                    bmp = mDataCache.getFromCache(client_id);
                } else {
                    bmp = covertToBitmap(jsonobject.getString("photo"));
                    if(bmp == null) {
                        throw new RuntimeException();
                    }
                    mDataCache.addToCache(client_id, bmp);
                    System.out.println("^^^");
                }

                ViewPagerItem viewPagerItem = new ViewPagerItem(
//                        placeholderImage,
                        bmp,
                        jsonobject.getString("first_name"),
                        jsonobject.getString("last_name"),
                        jsonobject.getString("address"),
                        jsonobject.getString("status"),
                        jsonObjectDetails.getInt("age"),
                        jsonObjectDetails.getString("email"),
                        jsonObjectDetails.getString("phone")
                );

                // If client_id matches, add to the front of the list
                if (jsonobject.getInt("client_id") != clientId) {
                    items.add(viewPagerItem);
                } else {
                    items.add(0, viewPagerItem);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return items;
    }

    public Bitmap covertToBitmap(String base64String) {
        try {
            // Remove any potential unwanted spaces/newlines (optional)
            base64String = base64String.trim();

            // Decode the Base64 string into a byte array
            byte[] decodedBytes = Base64.decode(base64String, Base64.DEFAULT);

            // Convert the byte array into a Bitmap
            return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);


        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            // Handle exception (e.g., if the input string is not valid Base64)
        }
        return null;
    }
}
