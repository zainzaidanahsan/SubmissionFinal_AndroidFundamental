package com.zain.submissionfinal_androidfundamental.ui

import androidx.test.core.app.ActivityScenario
import com.zain.submissionfinal_androidfundamental.R
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId

import org.junit.Before
import org.junit.Test

class MainActivityTest {

    @Before
    fun setUp() {
        ActivityScenario.launch(MainActivity::class.java)
    }
    @Test
    fun testRecyclerViewDisplayed() {
        Espresso.onView(withId(R.id.rvUser))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}