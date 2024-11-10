package com.example.assign3.DetailActivityRes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.assign3.R;

import java.util.ArrayList;
import java.util.List;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DropdownAdapter extends ArrayAdapter<StateVO> {
    private Context mContext;
    private ArrayList<StateVO> listState;
    private DropdownAdapter myAdapter;
    private boolean isFromView = false;
    private CheckBox checkedBox;
    private String domain = "http://10.0.2.2:5000";
    String authTok = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VybmFtZSI6ImpvaG5fZG9lIiwiZXhwIjoxNzMxMzU4NjIyfQ.amdQH5UPSY9PXmFBdp36JaiyAfDiU0PH19axePevtlc";
    private int clientId = 1;

    public DropdownAdapter(Context context, int resource, List<StateVO> objects, String authTok) {
        super(context, resource, objects);
        this.mContext = context;
        this.listState = (ArrayList<StateVO>) objects;
        this.myAdapter = this;
        this.authTok = authTok;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(final int position, View convertView,
                              ViewGroup parent) {

        final ViewHolder holder;
        if (convertView == null) {
            LayoutInflater layoutInflator = LayoutInflater.from(mContext);
            convertView = layoutInflator.inflate(R.layout.dropdown_item, null);
            holder = new ViewHolder();
            holder.mTextView = (TextView) convertView
                    .findViewById(R.id.textViewHidden);
            holder.mCheckBox = (CheckBox) convertView
                    .findViewById(R.id.checkbox);
            convertView.setTag(holder);

            // initialize holder with our db/array values
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.mCheckBox.setText(listState.get(position).getTitle());

        // To check whether checked event fires from getView() or user input
        isFromView = true;

        if (listState.get(position).isSelected()) {
            holder.mCheckBox.setChecked(true);
            checkedBox = holder.mCheckBox;
        } else {
            holder.mCheckBox.setChecked(false);
        }

        isFromView = false;

        if ((position == 0)) {
            holder.mTextView.setText("Status");
            holder.mCheckBox.setVisibility(View.INVISIBLE);
        } else {
            holder.mCheckBox.setVisibility(View.VISIBLE);
            holder.mTextView.setVisibility(View.INVISIBLE);
        }

        holder.mCheckBox.setTag(position);
        holder.mCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            int getPosition = (Integer) buttonView.getTag();

            if (!isFromView) {
                // Handle the state change based on checkbox click
                if (isChecked) {
                    // Case where unchecked box is checked
                    listState.get(getPosition).setSelected(true);
                    checkedBox.setChecked(false);
                    checkedBox = holder.mCheckBox;
                    System.out.println(listState.get(getPosition).getTitle());
                    String arg = String.format("{\"status\": \"%s\"}", listState.get(getPosition).getTitle());
                    updateClient(arg);
                    System.out.println("update to status occurred!");
                } else {
                    // Case where checked box is unchecked
                    listState.get(getPosition).setSelected(false);
                }
            }
        });

        return convertView;
    }

    private class ViewHolder {
        private TextView mTextView;
        private CheckBox mCheckBox;
    }

    private void updateClient(String jsonData) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // Create a URL object from the URL string
                    URL url = new URL(domain + "/clients/" + clientId);

                    // Open a connection to the URL
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                    // Set the HTTP method to PATCH
                    connection.setRequestMethod("PATCH");

                    // Set request headers (e.g., Content-Type for JSON)
                    connection.setRequestProperty("Content-Type", "application/json");
                    connection.setRequestProperty("Accept", "application/json");
                    connection.addRequestProperty("Authorization", authTok);

                    // Enable input/output streams
                    connection.setDoOutput(true);

                    // Write the JSON data to the output stream
                    DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
                    outputStream.writeBytes(jsonData);
                    outputStream.flush();
                    outputStream.close();

                    // Get the response code to check if the request was successful
                    int responseCode = connection.getResponseCode();
                    System.out.println("Response Code: " + responseCode);

                    // Close the connection
                    connection.disconnect();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
