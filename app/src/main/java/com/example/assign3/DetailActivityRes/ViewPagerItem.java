package com.example.assign3.DetailActivityRes;

public class ViewPagerItem {
    int imageId, age;
    String firstName, lastName, address, email, phone;
    Boolean[] status;


    public ViewPagerItem(int imageId, String firstName, String lastName, String address,
                         Boolean[] status, int age, String email, String phone) {
        this.imageId = imageId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.status = status;

        this.age = age;
        this.email = email;
        this.phone = phone;
    }
}
