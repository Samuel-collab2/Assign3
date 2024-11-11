package com.example.assign3;

import static android.os.SystemClock.sleep;

import org.junit.Rule;
import org.junit.Test;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.swipeRight;
import static androidx.test.espresso.action.ViewActions.swipeLeft;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
//import static androidx.test.espresso.matcher.ViewMatchers.withId;
//import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.StringContains.containsString;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static org.hamcrest.core.AllOf.allOf;

import android.content.Intent;

public class DetailViewUITest {
//    @Rule
//    public ActivityScenarioRule<DetailActivity> mActivityRule =
//            new ActivityScenarioRule<>(DetailActivity.class);

    @Test  /* change status check # assumes the 'completed' checkbox is UNCHECKED before opening status*/
    public void statusChangeCheck() throws Exception {

        // Prepare the Intent with the required extras
        Intent intent = new Intent(InstrumentationRegistry.getInstrumentation().getTargetContext(), DetailActivity.class);
        intent.putExtra("clientId", 3);  // example int extra
        intent.putExtra("authTok", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VybmFtZSI6ImpvaG5fZG9lIiwiZXhwIjoxNzMxMzU4NjIyfQ.amdQH5UPSY9PXmFBdp36JaiyAfDiU0PH19axePevtlc");  // example string extra

        // Launch the Activity with the Intent containing the extras
        try (ActivityScenario<DetailActivity> scenario = ActivityScenario.launch(intent)) {

            // Perform the Espresso tests after the Activity has been launched
            // onView(withId(R.id.textViewDetail)).check(matches(isDisplayed()));  // Example test: check if a view is displayed
            // Add more checks here based on your extras

            // Wait for page to populate with clients info
            sleep(25000);

            onView(withId(R.id.viewPager)).perform(swipeLeft());

            sleep(500);

            onView(withId(R.id.viewPager)).perform(swipeRight());

            sleep(500);

            onView(allOf(withId(R.id.spinner), isDisplayed())).perform(click());

            sleep(500);

            onView(withText("completed")).perform(click());
            sleep(500);

            onView(withText("completed")).
                    check(matches(isChecked())); // Check if checkbox is checked
        }
    }
}