package com.boundlesssystems.ninetest


import org.junit.Rule
import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import com.boundlesssystems.ninetest.base.BaseTest
import com.boundlesssystems.ninetest.model.CellData
import com.boundlesssystems.ninetest.viewmodel.NewsViewModel

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

import java.net.HttpURLConnection
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals


/** These series of tests are testing the networking functionality of the application. In order to do so a WEbMockServer is used **/
/** The REST server is mocked and the response that it gives comes from reading the "test.json" file in the resources folder **/
/** The output of the mocked webserver from the test.json file is compared with the EXPECTED_CELL_DATA created by hand **/
/** With this test the extraction of data from the web api response and the overall functionality of the application to the webserver responses is tested **/

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
class NewsStoriesUnitTest : BaseTest() {

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule() // Force tests to be executed synchronously

    // FOR DATA
    private lateinit var activity: FragmentActivity
    private lateinit var viewModel: NewsViewModel

    // Test data to check the output of the mocked webserver against
    private val EXPECTED_CELL_DATA = mutableListOf(CellData("Markets Live: ASX closes at 6026" ,
                                              "The S&P/ASX 200 has reached a fresh three month high after IT and industrial stocks added to yesterday's bank gains.",
                                              "Lucy Battersby" ,
                                              "https://www.fairfaxstatic.com.au/content/dam/images/h/1/2/7/m/i/image.related.thumbnail.375x250.h1awqy.13zzqx.png/1549431142591.jpg" ,
                                              1549431141239 ,"http://www.brisbanetimes.com.au/business/markets-live/markets-live-cbas47b-profit-20190205-h1awqy.html" ))

    // OVERRIDING
    override fun isMockServerEnabled(): Boolean = true

    @Before
    override fun setUp(){
        super.setUp()
        this.activity = Robolectric.setupActivity(FragmentActivity::class.java)
        this.viewModel = ViewModelProviders.of(this.activity, viewModelFactory)[NewsViewModel::class.java]
    }

    // Test when the server responds correctly
    @Test
    fun getNews_whenSuccess() {
        // Prepare data
        this.mockHttpResponse("test.json", HttpURLConnection.HTTP_OK)
        // Pre-test
        assertEquals(null, this.viewModel.newsToShow.value, "Output should be null because stream not started yet")
        // Execute View Model
        this.viewModel.getStories()
        // Checks

        assertEquals(EXPECTED_CELL_DATA, LiveDataTestUtil.getValue(this.viewModel.newsToShow), "News Data must be fetched")
        assertEquals(false, this.viewModel.loadingState.value, "Should be reset to 'false' because stream ended")
        assertEquals(null, this.viewModel.error.value, "No error must be founded")
    }

    // Test when the server responds incorrectly
    @Test
    fun getNews_whenError(){
        // Prepare data
        this.mockHttpResponse("test.json", HttpURLConnection.HTTP_BAD_GATEWAY)
        // Pre-test
        assertEquals(null, this.viewModel.newsToShow.value, "Output should be null because stream not started yet")
        // Execute View Model
        this.viewModel.getStories()
        // Checks
        assertEquals(null, this.viewModel.newsToShow.value, "Output must be null because of http error")
        assertEquals(false, this.viewModel.loadingState.value, "Should be reset to 'false' because stream ended")
        assertNotEquals(null, this.viewModel.error.value, "Error value must not be empty")
    }
}