package com.submission.dicoding.githubuser.ui

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.submission.dicoding.githubuser.R
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class ThemeActivityTest {

    @Before
    fun setup() {
        ActivityScenario.launch(ThemeActivity::class.java)
    }

    @Test
    fun testToggleSwitchTheme() {
        onView(withId(R.id.switch_theme)).check(matches(isDisplayed()))
        onView(withId(R.id.switch_theme)).perform(click())
    }

}