package com.sanitcode.footballclubapp.ui.activity.main

import android.support.test.espresso.Espresso
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.rule.ActivityTestRule
import android.support.v7.widget.RecyclerView
import com.sanitcode.footballclubapp.R
import com.sanitcode.footballclubapp.R.id.*
import com.sanitcode.footballclubapp.R.string.*
import org.junit.Rule
import org.junit.Test

class MainActivityTest {
    @Rule
    @JvmField
    var activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun testAppBehaviour() {
        //MatchModel
        Thread.sleep(1500)
        Espresso.onView(ViewMatchers.withId(nav_match)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(rv_match)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(5))
        Espresso.onView(ViewMatchers.withId(rv_match)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(5, ViewActions.click()))
        Espresso.onView(ViewMatchers.withId(add_to_favorite)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(add_to_favorite)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withText(added)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.pressBack()

        //TeamModel
        Thread.sleep(1500)
        Espresso.onView(ViewMatchers.withId(nav_team)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.layout_team_list)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Thread.sleep(1500)
        Espresso.onView(ViewMatchers.withId(rv_team)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(5))
        Espresso.onView(ViewMatchers.withId(rv_team)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(5, ViewActions.click()))
        Thread.sleep(1500)
        Espresso.onView(ViewMatchers.withId(add_to_favorite)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(add_to_favorite)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withText(added_team)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.pressBack()

        //Favorite MatchModel
        Thread.sleep(1500)
        Espresso.onView(ViewMatchers.withId(nav_favorite)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.fav_match_container)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Thread.sleep(1500)
        Espresso.onView(ViewMatchers.withId(rv_fav_match)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))
        Espresso.onView(ViewMatchers.withId(rv_fav_match)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, ViewActions.click()))
        Thread.sleep(3000)
        Espresso.onView(ViewMatchers.withId(add_to_favorite)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(add_to_favorite)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withText(removedb)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.pressBack()

        //Favorite TeamModel
        Thread.sleep(1500)
        Espresso.onView(ViewMatchers.withId(R.id.viewpager_fav)).perform(ViewActions.swipeLeft())
        Espresso.onView(ViewMatchers.withId(rv_fav_team)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))
        Espresso.onView(ViewMatchers.withId(rv_fav_team)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, ViewActions.click()))
        Thread.sleep(1500)
    }
}