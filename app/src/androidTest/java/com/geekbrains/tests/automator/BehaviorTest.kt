package com.geekbrains.tests.automator

import android.content.Context
import android.content.Intent
import android.view.KeyEvent
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SdkSuppress
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiObject2
import androidx.test.uiautomator.Until
import com.geekbrains.tests.R
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SdkSuppress(minSdkVersion = 18)
class BehaviorTest {

    private val uiDevice: UiDevice = UiDevice.getInstance(getInstrumentation())

    private val context = ApplicationProvider.getApplicationContext<Context>()

    private val packageName = context.packageName

    @Before
    fun setup() {

        uiDevice.pressHome()

        val intent = context.packageManager.getLaunchIntentForPackage(packageName)

        intent!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        context.startActivity(intent)

        uiDevice.wait(Until.hasObject(By.pkg(packageName).depth(0)), TIMEOUT)
    }

    @Test
    fun test_MainActivityIsStarted() {

        val editText = uiDevice.findObject(By.res(packageName, "searchEditText"))

        Assert.assertNotNull(editText)
    }

    @Test
    fun test_SearchIsPositive() {

        val editText = uiDevice.findObject(By.res(packageName, "searchEditText"))

        editText.text = "UiAutomator"

        Espresso.onView(ViewMatchers.withId(R.id.searchEditText))
            .perform(ViewActions.pressImeActionButton())

        val changedText =
            uiDevice.wait(
                Until.findObject(By.res(packageName, "totalCountTextView")),
                TIMEOUT
            )

        Assert.assertNotNull(changedText.text.toString())
    }

    @Test
    fun test_OpenDetailsScreen() {

        val toDetails: UiObject2 = uiDevice.findObject(
            By.res(
                packageName,
                "toDetailsActivityButton"
            )
        )
        toDetails.click()

        val changedText =
            uiDevice.wait(
                Until.findObject(By.res(packageName, "totalCountTextView")),
                TIMEOUT
            )
        Assert.assertEquals(changedText.text, "Number of results: 0")
    }

    @Test
    fun test_DetailsScreenShowCorrectText() {

        val editText = uiDevice.findObject(By.res(packageName, "searchEditText"))

        editText.text = "UiAutomator"

        uiDevice.findObject(
            By.res(packageName, "searchButton")
        ).click()

        //editText.click()
        //uiDevice.pressKeyCode(KeyEvent.KEYCODE_SEARCH)

        val changedText =
            uiDevice.wait(
                Until.findObject(By.res(packageName, "totalCountTextView")),
                TIMEOUT
            )

        val toDetails: UiObject2 = uiDevice.findObject(
            By.res(
                packageName,
                "toDetailsActivityButton"
            )
        )
        toDetails.click()

        Assert.assertEquals(changedText.text, "Number of results: 686")
    }

    @Test
    fun test_IncrementButtonShowCorrectText() {

        val toDetails: UiObject2 = uiDevice.findObject(
            By.res(
                packageName,
                "toDetailsActivityButton"
            )
        )
        toDetails.click()

        val toIncrease: UiObject2 = uiDevice.wait(
            Until.findObject(
                By.res(
                    packageName,
                    "incrementButton"
                )
            ),
            TIMEOUT
        )

        toIncrease.click()

        val changedText = uiDevice.findObject(
            By.res(
                packageName,
                "totalCountTextView"
            )
        )

        Assert.assertEquals("Number of results: $INCREASED_VALUE", changedText.text)
    }

    @Test
    fun test_DecrementButtonShowCorrectText() {

        val toDetails: UiObject2 = uiDevice.findObject(
            By.res(
                packageName,
                "toDetailsActivityButton"
            )
        )
        toDetails.click()

        val toIncrease: UiObject2 = uiDevice.wait(
            Until.findObject(
                By.res(
                    packageName,
                    "decrementButton"
                )
            ),
            TIMEOUT
        )

        toIncrease.click()

        val changedText = uiDevice.findObject(
            By.res(
                packageName,
                "totalCountTextView"
            )
        )

        Assert.assertEquals("Number of results: $DECREASED_VALUE", changedText.text)
    }

    @Test
    fun test_IsMainActivityFromDetailsScreen() {

        val toDetails: UiObject2 = uiDevice.findObject(
            By.res(
                packageName,
                "toDetailsActivityButton"
            )
        )
        toDetails.click()

        uiDevice.wait(
            Until.findObject(By.res(packageName, "totalCountTextView")),
            TIMEOUT
        )

        uiDevice.pressBack()

        val editText = uiDevice.wait(
            Until.findObject(By.res(packageName, "searchEditText")),
            TIMEOUT
        )

        Assert.assertNotNull(editText)
    }

    @Test
    fun test_IsMainActivityCloseOnBackButton() {

        uiDevice.pressBack()

        val editText = uiDevice.findObject(By.res(packageName, "searchEditText"))

        Assert.assertNull(editText)
    }

    companion object {
        private const val TIMEOUT = 10000L
        private const val INCREASED_VALUE = 1
        private const val DECREASED_VALUE = -1
    }
}
