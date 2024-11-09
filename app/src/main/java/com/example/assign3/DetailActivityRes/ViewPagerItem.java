package com.example.assign3.DetailActivityRes;

import android.graphics.Bitmap;

public class ViewPagerItem {
    int imageId, age;
    String firstName, lastName, address, email, phone;
    String status;

    Bitmap decodedBitmap;


    public ViewPagerItem(Bitmap imageId, String firstName, String lastName, String address,
                         String status, int age, String email, String phone) {
        decodedBitmap = imageId;
//        this.imageId = imageId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.status = status;

        this.age = age;
        this.email = email;
        this.phone = phone;
    }
}
