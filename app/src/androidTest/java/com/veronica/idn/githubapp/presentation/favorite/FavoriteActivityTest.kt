package com.veronica.idn.githubapp.presentation.favorite

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import com.veronica.idn.githubapp.R
import com.veronica.idn.githubapp.presentation.home.MainActivity
import junit.framework.TestCase
import org.junit.Before
import org.junit.Test

class FavoriteActivityTest : TestCase(){
    @Before
    fun setup() {
        ActivityScenario.launch(FavoriteActivity::class.java)
    }
    @Test
    fun showRecyclerView(){
        Espresso.onView(ViewMatchers.withId(R.id.rvFavorite))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}