package com.boundlesssystems.ninetest

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.filters.LargeTest
import androidx.test.runner.AndroidJUnit4
import org.junit.runner.RunWith
import com.boundlesssystems.ninetest.view.MainActivity
import androidx.test.rule.ActivityTestRule
import com.boundlesssystems.ninetest.view.WebViewActivity
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

/** This set of tests test the overall functionality of the recyclerview as the main interaction point (view) with the user using Espresso
 * and RecyclerViewActions */

@LargeTest
@RunWith(AndroidJUnit4::class)
class RecyclerViewTest {

    @Rule @JvmField
    val mActivityRule = ActivityTestRule(MainActivity::class.java)

    @Rule @JvmField
    val webViewActivityRule = ActivityTestRule(WebViewActivity::class.java)


    // Test to check clicking on an item in the recyclerview (the recyclerview scrolls correctly)
    @Test
    fun scrollToPosition() {
        Espresso.onView(ViewMatchers.withId(R.id.recycler_view))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(5 , ViewActions.click()))
    }

    // Test to check clicking on an item in the recyclerview (the corresponding story is loaded and shown in the webview)
    @Test
    fun userClicksRow_WebOpens() {
        Espresso.onView(ViewMatchers.withId(R.id.recycler_view))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(1 , ViewActions.click()))

        Assert.assertEquals(webViewActivityRule.activity.javaClass , WebViewActivity::class.java )
    }
}