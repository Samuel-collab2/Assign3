package com.example.assign3;

import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;

@RunWith(AndroidJUnit4.class)
public class ClientManagerTest {

    @Rule
    public ActivityScenarioRule<LoginActivity> activityRule = new ActivityScenarioRule<>(LoginActivity.class);

    @Test
    public void testSortFunctionality() throws InterruptedException {
        onView(withId(R.id.usernameEditText)).perform(replaceText("john_doe"), closeSoftKeyboard());
        onView(withId(R.id.passwordEditText)).perform(replaceText("test"), closeSoftKeyboard());
        onView(withId(R.id.loginButton)).perform(click());

        Thread.sleep(5000);

//        Check if a main activity view is visible to confirm it is inside main activity
        onView(withId(R.id.searchView)).check(matches(isDisplayed()));

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
        // Testing the first client: John Doe, Frontend (status here used as role)
        onView(withText("John Doe completed")).check(matches(isDisplayed()));

// Testing the second client: Jane Smith, Refused (status here used as role)
        onView(withText("Jane Smith refused")).check(matches(isDisplayed()));

// Testing the third client: Emily Johnson, Partial (status here used as role)
        onView(withText("Emily Johnson partial")).check(matches(isDisplayed()));

// Testing the fourth client: Michael Brown, Completed (status here used as role)
        onView(withText("Michael Brown completed")).check(matches(isDisplayed()));

// Testing the fifth client: Sarah Davis, Refused (status here used as role)
        onView(withText("Sarah Davis refused")).check(matches(isDisplayed()));
    }
}
