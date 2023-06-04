package com.submission.dicoding.githubuser.ui

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.submission.dicoding.githubuser.R
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest {

    @Before
    fun setup() {
        ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun testNavigationMain() {
        onView(withId(R.id.rv_listUser)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_listUser)).perform(click())

        onView(withId(R.id.search)).check(matches(isDisplayed()))
        onView(withId(R.id.search)).perform(click())

        onView(withId(R.id.menu_favorite)).check(matches(isDisplayed()))
        onView(withId(R.id.menu_favorite)).perform(click())
    }

}