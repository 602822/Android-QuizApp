package com.example.oblig1quizapp;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.hamcrest.CoreMatchers.allOf;
import static org.junit.Assert.assertEquals;

import android.content.Context;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.atomic.AtomicBoolean;


/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class QuizTests {
    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule
            = new ActivityScenarioRule<>(MainActivity.class);


    // 1/3 chance of passing
    //The test will pass if the answer selected was correct.
    //The test will fail if the answer selected was false.
    @Test
    public void testScoreUpdate() {

        ActivityScenario<QuizActivity> activityScenario = ActivityScenario.launch(QuizActivity.class);
        onView(withId(R.id.button)).perform(click());

        AtomicBoolean isOK = new AtomicBoolean(false);

        activityScenario.onActivity(a -> {
            String buttonText = a.button.getText().toString();
            isOK.set(a.dog.getImageText().equals(buttonText));

        });

        if (isOK.get()) {
            onView(withId(R.id.scoreText)).check(matches(withText("Your Score: 1")));

        } else {
            onView(withId(R.id.scoreText)).check(matches(withText("Your Score: 0")));

        }
    }


    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = getInstrumentation().getTargetContext();
        assertEquals("com.example.oblig1quizapp", appContext.getPackageName());
    }


}
