package com.veronica.idn.githubapp.presentation.detail

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import com.veronica.idn.githubapp.R
import junit.framework.TestCase
import org.junit.Before
import org.junit.Test

class DetailActivityTest : TestCase() {
    @Before
    fun setup() {
        ActivityScenario.launch(DetailActivity::class.java)
    }

    @Test
    fun showTabLayoutVisibility() {
        Espresso.onView(ViewMatchers.withId(R.id.tlDetail))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun showViewPagerVisibility() {
        Espresso.onView(ViewMatchers.withId(R.id.vpDetail))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}