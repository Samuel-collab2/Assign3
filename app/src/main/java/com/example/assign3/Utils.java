package com.example.assign3;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Utils {
    public static String readStream(InputStream in) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            result.append(line);
        }
        reader.close();
        return result.toString();
    }
}