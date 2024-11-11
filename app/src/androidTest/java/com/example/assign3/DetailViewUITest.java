package com.example.assign3;

import static android.os.SystemClock.sleep;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.swipeRight;
import static androidx.test.espresso.action.ViewActions.swipeLeft;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static org.hamcrest.core.AllOf.allOf;


@RunWith(AndroidJUnit4.class)
public class DetailViewUITest {

    @Rule
    public ActivityScenarioRule<LoginActivity> mActivityRule =
            new ActivityScenarioRule<>(LoginActivity.class);


    @Test  /* change status check # assumes the 'completed' checkbox is UNCHECKED before opening status*/
    public void statusChangeCheck() throws Exception {
        onView(withId(R.id.usernameEditText)).perform(replaceText("john_doe"), closeSoftKeyboard());
        onView(withId(R.id.passwordEditText)).perform(replaceText("test"), closeSoftKeyboard());
        onView(withId(R.id.loginButton)).perform(click());

        Thread.sleep(2000);

//        Check if a main activity view is visible to confirm it is inside main activity
        onView(withId(R.id.searchView)).check(matches(isDisplayed()));

        onView(withText("123 Main St, Springfield")).perform(click());

        // Wait for page to populate with client detailed info (takes long time)
        sleep(23000);

        onView(withId(R.id.viewPager)).perform(swipeLeft());

        sleep(500);

        onView(withId(R.id.viewPager)).perform(swipeRight());

        sleep(500);

        onView(allOf(withId(R.id.spinner), isDisplayed())).perform(click());

        sleep(500);

        onView(withText("partial")).perform(click());
        sleep(500);

        onView(withText("partial")).
                check(matches(isChecked())); // Check if checkbox is checked
    }
}