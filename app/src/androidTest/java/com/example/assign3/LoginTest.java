package com.example.assign3;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.espresso.matcher.ViewMatchers.isChecked;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class LoginTest {

    @Rule
    public ActivityScenarioRule<LoginActivity> activityScenarioRule =
            new ActivityScenarioRule<>(LoginActivity.class);

    // Consider mocking the network call using Mockito for better isolation (optional)
    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testSuccessfulLogin() throws InterruptedException {
        onView(withId(R.id.usernameEditText)).perform(clearText(), typeText("john_doe"));
        onView(withId(R.id.passwordEditText)).perform(clearText(), typeText("test"));
        closeSoftKeyboard();
        onView(withId(R.id.loginButton)).perform(click());
        Thread.sleep(2000);

//        Check if a main activity view is visible to confirm it is inside main activity
        onView(withId(R.id.searchView)).check(matches(isDisplayed()));
    }

    @Test
    public void testEmptyCredentials() throws InterruptedException {
        onView(withId(R.id.loginButton)).perform(click());
        Thread.sleep(2000);

//        Check if a login button is visible to confirm it is inside login activity
        onView(withId(R.id.loginButton)).check(matches(isDisplayed()));
    }

    @Test
    public void testInvalidCredentials() throws InterruptedException {
        // Enter invalid username and password
        onView(withId(R.id.usernameEditText)).perform(clearText(), typeText("invalid_username"));
        onView(withId(R.id.passwordEditText)).perform(clearText(), typeText("invalid_password"));
        closeSoftKeyboard();
        onView(withId(R.id.loginButton)).perform(click());
        Thread.sleep(2000);

//        Check if a login button is visible to confirm it is inside login activity
        onView(withId(R.id.loginButton)).check(matches(isDisplayed()));
    }
}