package com.geekbrains.tests

import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.geekbrains.tests.view.details.DetailsActivity
import com.geekbrains.tests.view.search.MainActivity
import junit.framework.TestCase
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityEspressoTest {

    private lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun setup() {
        scenario = ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun mainActivityApi_test() {
        MainActivityApiTest()
    }

    @Test
    fun activity_AssertNotNull() {
        scenario.onActivity {
            TestCase.assertNotNull(it)
        }
    }

    @Test
    fun activity_IsResumed() {
        TestCase.assertEquals(Lifecycle.State.RESUMED, scenario.state)
    }

    @Test
    fun activityToDetails_AreEffectiveVisible() {
        onView(withId(R.id.toDetailsActivityButton))
            .check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
    }

    @Test
    fun activitySearchEditText_IsEmptyString() {
        onView(withId(R.id.searchEditText))
            .check(matches(withText("")))
    }

    @Test
    fun activitySearchEditText_IsHintMatches() {
        onView(withId(R.id.searchEditText))
            .check(matches(withHint(R.string.search_hint)))
    }

    @Test
    fun activitySearchEditText_IsCompletelyDisplayed() {
        onView(withId(R.id.searchEditText))
            .check(matches(isCompletelyDisplayed()))
    }

    @Test
    fun activityToDetailsActivityButton_IsBtnTextMatches() {
        onView(withId(R.id.toDetailsActivityButton))
            .check(matches(withText(R.string.to_details)))
    }

    @Test
    fun activityToDetailsButton_IsOpenDetailsActivity() {
        onView(withId(R.id.toDetailsActivityButton)).perform(click())
        ActivityScenario.launch(DetailsActivity::class.java).apply {
            this.onActivity { detailsActivity ->
                TestCase.assertNotNull(detailsActivity)
            }
            this.close()
        }
    }

    @After
    fun close() {
        scenario.close()
    }
}
