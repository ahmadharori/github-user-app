package com.example.githubuserapi.ui

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId

import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.example.githubuserapi.R
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class DarkModeActivityTest {
    @Before
    fun setup(){
        ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun settingPageOpeningTest() {
        onView(withId(R.id.settings)).check(matches(isDisplayed()))
        onView(withId(R.id.settings)).perform(click())

        onView(withId(R.id.switch_theme)).check(matches(isDisplayed()))
    }
}