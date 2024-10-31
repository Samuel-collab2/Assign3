package com.example.assign3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    ViewPager2 viewPager2;
    ArrayList<ViewPagerItem> viewPagerItemArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Can pass info from layout to layout via intents:
        String name = getIntent().getStringExtra("keyForName");
        String address = getIntent().getStringExtra("keyForAddress");

        viewPager2 = findViewById(R.id.viewPager);
        int[] images = {R.drawable.pika, R.drawable.charma};
        String[] firstNames = {"Rachel", "Ryan"};
        String[] lastNames = {"Adam", "Lee"};
        String[] addresses = {"234 pineapple ave", "3334 north road"};
        Boolean[] statusRachel = {true, false, false};
        Boolean[] statusAdam = {true, false, true};
        Boolean[][] status = {statusRachel, statusAdam};


        viewPagerItemArrayList = new ArrayList<>();

        for (int i =0; i < images.length; i++) {
            ViewPagerItem viewPagerItem =
                    new ViewPagerItem(images[i], firstNames[i], lastNames[i], addresses[i],
                            status[i]);
            viewPagerItemArrayList.add(viewPagerItem);
        }

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(viewPagerItemArrayList, DetailActivity.this);

        viewPager2.setAdapter(viewPagerAdapter);
        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(2);
        viewPager2.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);

        // configureReturnButton();
    }

//    public void configureReturnButton(){
//        Button detailButton = (Button) findViewById(R.id.backButton);
//        detailButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
//    }
}