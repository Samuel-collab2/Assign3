package com.example.assign3;

public class ViewPagerItem {
    int imageId;
    String firstName, lastName, address;
    Boolean[] status;


    public ViewPagerItem(int imageId, String firstName, String lastName, String address,
                         Boolean[] status) {
        this.imageId = imageId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.status = status;
    }
}
