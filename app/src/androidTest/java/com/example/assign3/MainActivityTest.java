package com.example.assign3;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testSearchAndSortFunctionality() {
        // Ensure the search view is present and type "Anadi"
        onView(withId(R.id.searchView)).check(matches(ViewMatchers.isDisplayed()));
        onView(withId(R.id.searchView)).perform(typeText("Anadi"));

        // Ensure the sort spinner is present and open it
        onView(withId(R.id.sortSpinner)).check(matches(ViewMatchers.isDisplayed()));
        onView(withId(R.id.sortSpinner)).perform(click());

        // Sleep for 500 ms to ensure the dropdown is fully opened
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Click on the "Sort by first name" option
        onView(withText("Sort by First Name")).perform(click());

        // Verify that the sorted list displays "Anadi" at the top
        onView(withText("Anadi Frontend")).check(matches(ViewMatchers.isDisplayed()));

        // Verify that other clients are also displayed
        onView(withText("Simar Login")).check(matches(ViewMatchers.isDisplayed()));
        onView(withText("Dhruv Backend")).check(matches(ViewMatchers.isDisplayed()));

        // Additionally, you can verify the address if needed
        onView(withText("Newton, BC")).check(matches(ViewMatchers.isDisplayed()));
    }
}
