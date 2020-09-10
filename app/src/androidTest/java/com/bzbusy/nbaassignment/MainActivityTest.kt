package com.bzbusy.nbaassignment

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.rule.ActivityTestRule
import com.bzbusy.nbaassignment.adapter.NbaTeamListAdapter
import junit.framework.Assert.assertEquals
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.not
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
@RunWith(AndroidJUnit4ClassRunner::class)
@LargeTest
class MainActivityTest {
    @Rule
    @JvmField
    var activityRule = ActivityTestRule<MainActivity>(MainActivity::class.java)

    private lateinit var mActivity: MainActivity

    @Before
    fun setUp() {
        mActivity = activityRule.activity
    }

    @Test
    fun useAppContext() { // Context of the app under test.
        val appContext = getInstrumentation().targetContext
        Assert.assertEquals("com.bzbusy.nbaassignment", appContext.packageName)
    }

    @Test
    fun test_isTeamRecyclerView_visible_onAppLaunch() {
        onView(withId(R.id.rootMain)).check(matches(isDisplayed()))
        onView(withId(R.id.recyclerViewTeamList)).check(matches(isDisplayed()))
    }

    @Test
    fun test_isTeamRecyclerView_Adaptor() {
        val recyclerView: RecyclerView = mActivity.findViewById(R.id.recyclerViewTeamList)
        assertEquals(recyclerView.adapter!!.itemCount, 30)
    }

    @Test
    fun test_TeamRecyclerViewScroll() {
        val recyclerView: RecyclerView = mActivity.findViewById(R.id.recyclerViewTeamList)
        val totalCount: Int = recyclerView.adapter!!.itemCount
        // Test scroll down to bottom
        onView(withId(R.id.recyclerViewTeamList)).perform(scrollToPosition<NbaTeamListAdapter.NbaListViewHolder>(
                totalCount - 1))
        // Test scroll up to top
        onView(withId(R.id.recyclerViewTeamList)).perform(scrollToPosition<NbaTeamListAdapter.NbaListViewHolder>(0))
        // Test scroll down to bottom
        onView(withId(R.id.recyclerViewTeamList)).perform(scrollToPosition<NbaTeamListAdapter.NbaListViewHolder>(
                totalCount - 1))
        // Test scroll up to top
        onView(withId(R.id.recyclerViewTeamList)).perform(scrollToPosition<NbaTeamListAdapter.NbaListViewHolder>(0))
    }

    @Test
    fun test_MenuItemClicked() {
        // Click menu button, click 'Wins'
        openActionBarOverflowOrOptionsMenu(getInstrumentation().targetContext);
        onView(withText(R.string.team_filter_win)).perform(click())

        // Verify the toast message
        onView(withText(R.string.team_sort_by_win)).inRoot(withDecorView(not(`is`(mActivity.window?.decorView)))).check(matches(isDisplayed()))
    }
}