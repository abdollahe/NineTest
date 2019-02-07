package com.boundlesssystems.ninetest.base

import com.boundlesssystems.ninetest.BASE_URL
import com.boundlesssystems.ninetest.di.*
import com.boundlesssystems.ninetest.viewmodel.ViewModelFactory
import com.squareup.okhttp.mockwebserver.MockResponse
import com.squareup.okhttp.mockwebserver.MockWebServer

import org.junit.After
import org.junit.Before
import java.io.File
import javax.inject.Inject

/** In this baseTest Class , Dagger is setup so that it provides the mocked webserver to the test application instead of the actual
 * webserver interface **/
abstract class BaseTest {

    lateinit var testAppComponent: TestAppComponent
    lateinit var mockServer: MockWebServer
    @Inject lateinit var viewModelFactory: ViewModelFactory

    @Before
    open fun setUp(){
        this.configureMockServer()
        this.configureDi()
    }

    @After
    open fun tearDown(){
        this.stopMockServer()
    }

    // CONFIGURATION
    open fun configureDi(){
        this.testAppComponent = DaggerTestAppComponent.builder()
                .netModule(NetModule(if (isMockServerEnabled()) mockServer.url("/").toString() else BASE_URL))
                .repositoryModule(RepositoryModule())
                .testRxJavaModule(TestRxJavaModule())
                .build()
        this.testAppComponent.inject(this)
    }

    // MOCK SERVER
    abstract fun isMockServerEnabled(): Boolean // Because we don't want it always enabled on all tests

    open fun configureMockServer(){
        if (isMockServerEnabled()){
            mockServer = MockWebServer()
            mockServer.start()
        }
    }

    open fun stopMockServer() {
        if (isMockServerEnabled()){
            mockServer.shutdown()
        }
    }

    open fun mockHttpResponse(fileName: String, responseCode: Int) = mockServer.enqueue(MockResponse()
            .setResponseCode(responseCode)
            .setBody(getJson(fileName)))


    // Function for reading json files from the resources folder
    private fun getJson(path : String) : String {
        val uri = this.javaClass.classLoader.getResource(path)
        val file = File(uri.path)
        return String(file.readBytes())
    }
}