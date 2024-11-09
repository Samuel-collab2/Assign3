package com.example.assign3.DetailActivityRes;

import android.graphics.Bitmap;

import java.util.BitSet;
import java.util.HashMap;

public class DataCache {
    private HashMap<Integer, Bitmap> cache = new HashMap<>();

    // Add data to cache
    public void addToCache(Integer key, Bitmap value) {
        cache.put(key, value);
    }

    // Get data from cache
    public Bitmap getFromCache(Integer key) {
        return cache.get(key); // Return null if key doesn't exist
    }

    // Check if cache contains the key
    public boolean isCached(Integer key) {
        return cache.containsKey(key);
    }
}
