package com.bzbusy.nbaassignment

import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.bzbusy.nbaassignment.adapter.NbaTeamListAdapter
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class DetailActivityTest {
    @Rule
    @JvmField
    var activityRule = ActivityTestRule<MainActivity>(MainActivity::class.java)

    @Test
    fun test_ActivityNavigation() {
        // Detail root view gone
        onView(withId(R.id.rootDetail)).check(doesNotExist())

        // Recycler view item clicked, go to detail page
        onView(withId(R.id.recyclerViewTeamList))
                .perform(actionOnItemAtPosition<NbaTeamListAdapter.NbaListViewHolder>(1, click()))

        // Detail root view displayed
        onView(withId(R.id.rootDetail)).check(matches(isDisplayed()))

        Espresso.pressBack()

        // Detail root view gone
        onView(withId(R.id.rootDetail)).check(doesNotExist())
    }
}