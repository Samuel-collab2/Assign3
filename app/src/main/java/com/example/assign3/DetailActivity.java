package com.example.assign3;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;
import com.example.assign3.DetailActivityRes.ViewPagerAdapter;
import com.example.assign3.DetailActivityRes.ViewPagerItem;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class DetailActivity extends AppCompatActivity {

    ViewPager2 viewPager2;
    ArrayList<ViewPagerItem> viewPagerItemArrayList;

    interface RequestUser {
        @GET("http://127.0.0.1:5000/clients/{uid}")
        Call<ViewPagerItem> getClient(@Path("uid") String uid):
    }


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

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://127.0.0.1:5000")
                .addConverterFactory(GsonConverterFactory)

        // Can pass info from layout to layout via intents:
        String name = getIntent().getStringExtra("keyForName");
        String address = getIntent().getStringExtra("keyForAddress");


        viewPager2 = findViewById(R.id.viewPager);
        int[] images = {R.drawable.pika, R.drawable.charma};
        String[] firstNames = {"Rachel", "Ryan"};
        String[] lastNames = {"Adam", "Lee"};
        String[] addresses = {"234 pineapple ave", "3334 north road"};
        Boolean[] statusRachel = {true, false, false};
        Boolean[] statusAdam = {false, false, true};
        Boolean[][] status = {statusRachel, statusAdam};

        Integer[] age = {19, 23};
        String[] email = {"first@email.com", "second@email.com"};
        String[] phone = {"555-123", "555-888"};


        viewPagerItemArrayList = new ArrayList<>();


        for (int i =0; i < images.length; i++) {
            ViewPagerItem viewPagerItem =
                    new ViewPagerItem(images[i], firstNames[i], lastNames[i], addresses[i],
                            status[i], age[i], email[i], phone[i]);
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