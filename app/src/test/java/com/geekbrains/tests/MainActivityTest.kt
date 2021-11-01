package com.geekbrains.tests

import android.os.Build
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.geekbrains.tests.view.search.MainActivity
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import junit.framework.TestCase
import kotlinx.android.synthetic.main.activity_main.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowLooper
import org.robolectric.shadows.ShadowToast

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
class MainActivityTest {

    private lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun setup() {
        scenario = ActivityScenario.launch(MainActivity::class.java)
    }

    @After
    fun endTest() {
        scenario.close()
    }

    @Test
    fun mainActivity_AssertNotNull() {
        scenario.onActivity {
            assertNotNull(it)
        }
    }

    @Test
    fun mainActivity_IsResumed() {
        assertEquals(Lifecycle.State.RESUMED, scenario.state)
    }

    @Test
    fun mainActivity_SearchEditText_IsVisible() {
        scenario.onActivity {
            assertNotNull(it.searchEditText)
            assertEquals(View.VISIBLE, it.searchEditText.visibility)
        }
    }

    @Test
    fun mainActivity_ToDetailsActivityButton_IsVisible() {
        scenario.onActivity {
            assertNotNull(it.toDetailsActivityButton)
            assertEquals(View.VISIBLE, it.toDetailsActivityButton.visibility)
        }
    }

    @Test
    fun mainActivity_RecycleView_IsVisible() {
        scenario.onActivity {
            assertNotNull(it.recyclerView)
            assertEquals(View.VISIBLE, it.recyclerView.visibility)
        }
    }

    @Test
    fun mainActivity_ProgressButton_IsVisible() {
        scenario.onActivity {
            assertNotNull(it.progressBar)
            assertEquals(View.VISIBLE, it.toDetailsActivityButton.visibility)
        }
    }

    @Test
    fun mainActivity_EmptyRequestShowToast_ReturnTrue() {
        scenario.onActivity {
            with(it.searchEditText) {
                setText("", TextView.BufferType.EDITABLE)
                onEditorAction(EditorInfo.IME_ACTION_SEARCH)
            }
            ShadowLooper.idleMainLooper()
            assertEquals(ShadowToast.getTextOfLatestToast(), EMPTY_REQUEST_TOAST)
        }
    }

    companion object {
        private const val EMPTY_REQUEST_TOAST = "Enter search word"
    }
}