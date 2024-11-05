package com.example.assign3;

import org.junit.Rule;
import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import androidx.test.ext.junit.rules.ActivityScenarioRule;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

public class DetailViewUITest {
    @Rule
    public ActivityScenarioRule<MainActivity> mActivityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test  /* Find Photos Test*/
    public void timeBasedSearch() throws Exception {


        //Find and Click the Search Button
        onView(withId(R.id.btnSearch)).perform(click());

        //Find From and To fields in the Search layout and fill these with the above test data
        String from = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(startTimestamp);
        String to = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(endTimestamp);
        onView(withId(R.id.etFromDateTime)).perform(replaceText(from), closeSoftKeyboard());
        onView(withId(R.id.etToDateTime)).perform(replaceText(to), closeSoftKeyboard());
        onView(withId(R.id.etKeywords)).perform(replaceText(""), closeSoftKeyboard());

        //Find and Click the GO button on the Search View
        onView(withId(R.id.go)).perform(click());

        //Verify that the timestamp of the found Image matches the Expected value
        onView(withId(R.id.tvTimestamp)).check(matches(withText("20210227_132142")));
    }
}