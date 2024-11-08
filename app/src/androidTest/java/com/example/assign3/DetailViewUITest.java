package com.example.assign3;

import static android.os.SystemClock.sleep;

import org.junit.Rule;
import org.junit.Test;

import androidx.test.ext.junit.rules.ActivityScenarioRule;


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

public class DetailViewUITest {
    @Rule
    public ActivityScenarioRule<DetailActivity> mActivityRule =
            new ActivityScenarioRule<>(DetailActivity.class);

    @Test  /* change status check*/
    public void statusChangeCheck() throws Exception {

        onView(withId(R.id.viewPager)).perform(swipeLeft());

        sleep(500);

        onView(withId(R.id.viewPager)).perform(swipeRight());

        sleep(500);

        onView(allOf(withId(R.id.spinner), isDisplayed())).perform(click());

        sleep(500);
        onView(withText("refused")).perform(click());
        sleep(500);

        boolean checkedResult = true;
        // Optionally, add assertions if needed, for example:
//        onView(withText("completed")).;

        onView(withText("completed")).
                check(matches(isChecked())); // Check if checkbox is checked

    }
}