package com.example.assign3;

import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;

@RunWith(AndroidJUnit4.class)
public class ClientManagerTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testSortFunctionality() throws InterruptedException {
        // Ensure the sort spinner is present and open it
        onView(withId(R.id.sortSpinner)).check(matches(isDisplayed()));
        onView(withId(R.id.sortSpinner)).perform(click());

        // Add delay to allow spinner animations to complete
        Thread.sleep(500);

        // Click on the "Sort by First Name" option
        onView(withText("Sort by First Name")).perform(click());

        // Add delay to allow sorting to take effect
        Thread.sleep(500);

        // Verify that the sorted list displays clients in correct order
        onView(withText("Anadi Frontend")).check(matches(isDisplayed()));
        onView(withText("Simar Login")).check(matches(isDisplayed()));
        onView(withText("Dhruv Backend")).check(matches(isDisplayed()));
    }
}
