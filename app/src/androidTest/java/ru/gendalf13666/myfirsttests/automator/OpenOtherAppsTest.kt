package ru.gendalf13666.myfirsttests.automator

import android.widget.TextView
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SdkSuppress
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiScrollable
import androidx.test.uiautomator.UiSelector
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import ru.gendalf13666.myfirsttests.TestConstants

@RunWith(AndroidJUnit4::class)
@SdkSuppress(minSdkVersion = 18)
class OpenOtherAppsTest {

    private val uiDevice: UiDevice =
        UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

    @Test
    fun test_OpenSettings() {
        uiDevice.pressHome()
        uiDevice.swipe(
            TestConstants.SWIPE_START_X, TestConstants.SWIPE_START_Y,
            TestConstants.SWIPE_END_X, TestConstants.SWIPE_END_Y, TestConstants.SWIPE_STEPS
        )

        val appViews = UiScrollable(UiSelector().scrollable(true))

        val settingsApp = appViews
            .getChildByText(
                UiSelector()
                    .className(TextView::class.java.name),
                TestConstants.SETTINGS
            )
        settingsApp.clickAndWaitForNewWindow()

        val settingsValidation =
            uiDevice.findObject(UiSelector().packageName(TestConstants.SETTING_PACKAGE))
        Assert.assertTrue(settingsValidation.exists())
    }
}