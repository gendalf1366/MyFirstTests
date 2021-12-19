package ru.gendalf13666.myfirsttests.automator

import android.content.Context
import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SdkSuppress
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiObject2
import androidx.test.uiautomator.Until
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import ru.gendalf13666.myfirsttests.TestConstants

@RunWith(AndroidJUnit4::class)
@SdkSuppress(minSdkVersion = 18)
class BehaviorTest {

    private val uiDevice: UiDevice =
        UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())


    private val context = ApplicationProvider.getApplicationContext<Context>()


    private val packageName = context.packageName

    @Before
    fun setup() {

        uiDevice.pressHome()

        val intent = context.packageManager.getLaunchIntentForPackage(packageName)

        intent!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        context.startActivity(intent)

        uiDevice.wait(
            Until.hasObject(By.pkg(packageName).depth(TestConstants.EXACT_DEPT)),
            TestConstants.TIMEOUT
        )
    }

    @Test
    fun test_MainActivityIsStarted() {
        val editText = uiDevice.findObject(By.res(packageName, TestConstants.SEARCH_EDIT_TEXT_ID))
        Assert.assertNotNull(editText)
    }

    @Test
    fun test_SearchIsPositive() {
        val editText = uiDevice.findObject(By.res(packageName, TestConstants.SEARCH_EDIT_TEXT_ID))
        editText.text = TestConstants.QUERY_SECOND
        uiDevice.findObject(By.res(packageName, TestConstants.searchButton)).click()
        val changedText =
            uiDevice.wait(
                Until.findObject(By.res(packageName, TestConstants.TOTAL_COUNT)),
                TestConstants.TIMEOUT
            )
        Assert.assertEquals(changedText.text.toString(), TestConstants.FIND_RESULT_STRING)
    }

    @Test
    fun test_OpenDetailsScreen() {
        val toDetails: UiObject2 = uiDevice.findObject(
            By.res(
                packageName,
                TestConstants.TO_DETAILS_BUTTON_ID
            )
        )
        toDetails.click()

        val changedText =
            uiDevice.wait(
                Until.findObject(By.res(packageName, TestConstants.TOTAL_COUNT)),
                TestConstants.TIMEOUT
            )
        Assert.assertEquals(changedText.text, "Number of results: 0")
    }

    @Test
    fun test_OpenDetailsScreenAndCheckResult() {
        uiDevice.findObject(By.res(packageName, TestConstants.SEARCH_EDIT_TEXT_ID)).text =
            TestConstants.QUERY
        uiDevice.findObject(By.res(packageName, TestConstants.searchButton)).click()
        val resultValue =
            uiDevice.wait(
                Until.findObject(By.res(packageName, TestConstants.TOTAL_COUNT)),
                TestConstants.TIMEOUT
            ).text.toString()
        uiDevice.findObject(
            By.res(
                packageName,
                TestConstants.TO_DETAILS_BUTTON_ID
            )
        ).click()
        val detailValue = uiDevice.wait(
            Until.findObject(By.res(packageName, TestConstants.TOTAL_COUNT)),
            TestConstants.TIMEOUT
        ).text.toString()
        Assert.assertEquals(resultValue, detailValue)
    }

    @Test
    fun test_ShowProgressWhenSearch() {
        val editText = uiDevice.findObject(By.res(packageName, TestConstants.SEARCH_EDIT_TEXT_ID))
        editText.text = TestConstants.QUERY_SECOND
        uiDevice.findObject(By.res(packageName, TestConstants.searchButton)).click()
        val progress = uiDevice.wait(
            Until.findObject(
                By.res(
                    packageName,
                    TestConstants.PROGRESS_BAR_ID
                )
            ),
            TestConstants.TIMEOUT
        )
        Assert.assertNotNull(progress)
    }

    @Test
    fun test_NotShowProgressWhenSearchWithEmptyQuery() {
        val editText = uiDevice.findObject(By.res(packageName, TestConstants.SEARCH_EDIT_TEXT_ID))
        editText.text = ""
        uiDevice.findObject(By.res(packageName, TestConstants.searchButton)).click()
        val progress = uiDevice.wait(
            Until.findObject(
                By.res(
                    packageName,
                    TestConstants.PROGRESS_BAR_ID
                )
            ),
            TestConstants.TIMEOUT
        )
        Assert.assertNull(progress)
    }

    @Test
    fun test_ClickToIncrementAndCheckValue() {
        uiDevice.findObject(By.res(packageName, TestConstants.TO_DETAILS_BUTTON_ID)).click()
        uiDevice.wait(
            Until.findObject(
                By.res(
                    packageName,
                    TestConstants.INCREMENT_BUTTON_ID
                )
            ),
            TestConstants.TIMEOUT
        )?.click()
        val detailValue =
            uiDevice.findObject(
                By.res(packageName, TestConstants.TOTAL_COUNT)
            ).text.toString()
        Assert.assertEquals(detailValue, TestConstants.FIND_RESULT_INCREMENT)
    }

    @Test
    fun test_ClickToDecrimentAndCheckValue() {
        uiDevice.findObject(By.res(packageName, TestConstants.TO_DETAILS_BUTTON_ID)).click()
        uiDevice.wait(
            Until.findObject(
                By.res(
                    packageName,
                    TestConstants.DECREMENT_BUTTON_ID
                )
            ),
            TestConstants.TIMEOUT
        )?.click()

        val detailValue =
            uiDevice.findObject(
                By.res(packageName, TestConstants.TOTAL_COUNT)
            ).text.toString()
        Assert.assertEquals(detailValue, TestConstants.FIND_RESULT_DECREMENT)
    }

    @Test
    fun test_ClickBackFromDetailActivity() {
        uiDevice.findObject(By.res(packageName, TestConstants.TO_DETAILS_BUTTON_ID)).click()
        uiDevice.wait(
            Until.findObject(
                By.res(
                    packageName,
                    TestConstants.DECREMENT_BUTTON_ID
                )
            ),
            TestConstants.TIMEOUT
        )
        uiDevice.pressBack()
        val findButton = uiDevice.wait(
            Until.findObject(
                By.res(
                    packageName,
                    TestConstants.searchButton
                )
            ),
            TestConstants.TIMEOUT
        )
        Assert.assertNotNull(findButton)
    }

    @Test
    fun test_ClickBackFromMainActivity() {
        uiDevice.pressBack()
        val findButton = uiDevice.wait(
            Until.findObject(
                By.res(
                    packageName,
                    TestConstants.searchButton
                )
            ),
            TestConstants.TIMEOUT
        )
        Assert.assertNull(findButton)
    }
}