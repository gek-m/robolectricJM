package com.geekbrains.tests

import androidx.core.os.bundleOf
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.geekbrains.test.DEC_DEFAULT_VALUE
import com.geekbrains.test.DEFAULT_VALUE_10
import com.geekbrains.test.INC_DEFAULT_VALUE
import com.geekbrains.test.NUMBER_RESULT_TEXT
import com.geekbrains.tests.view.details.DetailsFragment
import junit.framework.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DetailsFragmentTest {

    private lateinit var scenario: FragmentScenario<DetailsFragment>

    @Before
    fun setup() {
        //scenario = launchFragmentInContainer(initialState = Lifecycle.State.INITIALIZED)
        scenario = launchFragmentInContainer()
    }

    @Test
    fun detailsFragment_isExist() {
        scenario.onFragment {
            Assert.assertNotNull(it)
        }
    }

    @Test
    fun detailsFragment_isBundleReceived() {
        val arg = bundleOf(DetailsFragment.TOTAL_COUNT_EXTRA to 5)
        val scenario = launchFragmentInContainer<DetailsFragment>(arg)
        scenario.moveToState(Lifecycle.State.RESUMED)

        onView(withId(R.id.totalCountTextView))
            .check(matches(withText(NUMBER_RESULT_TEXT + 5)))
    }

    @Test
    fun detailsFragment_isSetCountCorrect() {
        scenario.onFragment { fragment ->
            fragment.setCount(DEFAULT_VALUE_10)
        }
        val assertion = matches(withText(NUMBER_RESULT_TEXT + DEFAULT_VALUE_10))
        onView(withId(R.id.totalCountTextView)).check(assertion)
    }

    @Test
    fun detailsFragment_isIncrementButtonWorks() {
        onView(withId(R.id.incrementButton)).perform(click())
        onView(withId(R.id.totalCountTextView))
            .check(matches(withText(NUMBER_RESULT_TEXT + INC_DEFAULT_VALUE)))
    }

    @Test
    fun detailsFragment_isDecrementButtonWorks() {
        onView(withId(R.id.decrementButton)).perform(click())
        onView(withId(R.id.totalCountTextView))
            .check(matches(withText(NUMBER_RESULT_TEXT + DEC_DEFAULT_VALUE)))
    }
}