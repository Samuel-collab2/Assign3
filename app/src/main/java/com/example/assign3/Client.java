package com.example.assign3;

import java.util.Arrays;
import java.util.List;

public class Client {
    private int photo;
    private int id;
    private String firstName;
    private String lastName;
    private String address;
    private List<String> statusOptions;

    public Client(int photo,   String firstName, String lastName, String address,int id) {
        this.photo = photo;
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.statusOptions = Arrays.asList("Active", "Inactive", "Pending"); // Default options
    }

    public int getPhoto() {
        return photo;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public List<String> getStatusOptions() {
        return statusOptions;
    }

    public int getId() {
        return id;
    }
}
