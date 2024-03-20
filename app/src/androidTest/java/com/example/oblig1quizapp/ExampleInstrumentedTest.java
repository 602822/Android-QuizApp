package com.example.oblig1quizapp;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;

import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intending;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasAction;

import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;

import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;


import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.allOf;
import static org.junit.Assert.*;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;


/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule
            = new ActivityScenarioRule<>(MainActivity.class);



    /*

    @Before
    public void initIntents() {
        Intents.init();
    }

    @After
    public void releaseIntents() {
        Intents.release();
    }
*/


    @Test
    public void testGalleryButton() {
        onView(withId(R.id.gallerybutton)).perform(click());
        onView(withId(R.id.gridview)).check(matches(isDisplayed())); //check that the activity changed to Gallery

    }



    @Test
    public void testQuizButton() {
        onView(withId(R.id.quizButton)).perform(click());
        onView(withId(R.id.quizDogImage)).check(matches(isDisplayed())); //check that the activity changed to Quiz
    }



    /*
    @Test
    public void testPickImage() {
        Intent intent = new Intent();
        Uri uri = Uri.parse("test");
        intent.putExtra("imageUri",uri);

        Instrumentation.ActivityResult activityResult = new Instrumentation.ActivityResult(Activity.RESULT_OK, intent);
        onView(withId(R.id.gallerybutton)).perform(click());
        onView(withId(R.id.addbutton)).perform(click());
        intending(hasAction(Intent.ACTION_GET_CONTENT)).respondWith(activityResult);
    }
*/




    @Test
    public void checkRoundsUpdates() {
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), QuizActivity.class);
        ActivityScenario<QuizActivity> scenario = ActivityScenario.launch(intent);
        onView(withId(R.id.button)).perform(click());
        onView(withId(R.id.rounds)).check(matches(withText("Round: 2")));
    }

    @Test
    public void testDeleteImage() {
        onView(withId(R.id.gallerybutton)).perform(click());
        onView(withId(R.id.gridview)).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.textView), withText("Golden Retriever"))).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.textView), withText("Golden Retriever"))).perform(click());
        onView(allOf(withId(R.id.textView), withText("Golden Retriever"))).check(doesNotExist());
    }


    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = getInstrumentation().getTargetContext();
        assertEquals("com.example.oblig1quizapp", appContext.getPackageName());
    }


}