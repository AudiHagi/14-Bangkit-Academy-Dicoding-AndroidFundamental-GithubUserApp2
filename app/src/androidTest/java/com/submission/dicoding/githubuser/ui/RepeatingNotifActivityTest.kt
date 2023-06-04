package com.submission.dicoding.githubuser.ui

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith
import com.submission.dicoding.githubuser.R
import org.junit.Test


@RunWith(AndroidJUnit4ClassRunner::class)
class RepeatingNotifActivityTest{

    @Before
    fun setup() {
        ActivityScenario.launch(RepeatingNotifActivity::class.java)
    }

    @Test
    fun testButtonNotification() {
        onView(withId(R.id.btn_set_repeating_alarm)).perform(click())
        onView(withId(R.id.btn_cancel_repeating_alarm)).perform(click())
    }

}