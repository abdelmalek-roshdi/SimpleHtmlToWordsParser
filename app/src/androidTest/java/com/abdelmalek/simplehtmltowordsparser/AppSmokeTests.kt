package com.abdelmalek.simplehtmltowordsparser

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class AppSmokeTests {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setup() {
        // enable WIFI
        UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
            .executeShellCommand("svc wifi enable")
    }

    @Test
    fun initialAppLaunching_shouldBeInLoadingState() {
        val progressBar = onView(
            withId(R.id.loading_progress_bar)
        )

        progressBar.check(matches(isDisplayed()))
    }
}
