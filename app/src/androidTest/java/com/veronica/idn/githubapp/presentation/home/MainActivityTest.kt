package com.veronica.idn.githubapp.presentation.home

import androidx.test.core.app.ActivityScenario
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import junit.framework.TestCase
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.veronica.idn.githubapp.R
import org.junit.Before

import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest : TestCase(){
    @Before
    fun setup() {
        ActivityScenario.launch(MainActivity::class.java)
    }
    @Test
    fun showRecyclerView(){
        onView(withId(R.id.rvMain)).check(matches(isDisplayed()))
    }
    @Test
    fun testVisibilitySearchView() {
        onView(withId(R.id.etSearch)).check(matches(isDisplayed()))
    }

}