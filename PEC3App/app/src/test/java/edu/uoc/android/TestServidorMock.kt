package edu.uoc.android

import junit.framework.Assert.assertEquals
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test


class TestServidorMock (){

    private lateinit var mockServer: MockWebServer

    @Before
    fun getUp(){
        //1)
        this.mockServer = MockWebServer()
        //this.mockServer.start()
        //2)
        val dispatcher = object : Dispatcher() {
            @Throws(InterruptedException::class)
            override fun dispatch(request: RecordedRequest): MockResponse {
                return when (request.path) {
                    "/api/dataset/museus/" -> {
                        val response = MockResponse()
                            .setResponseCode(202)
                            .setBody(Utils.BODY)
                    }
                    // TODO, if you need other unit test for other methods
                    else -> MockResponse().setResponseCode(404)
                } as MockResponse
            }
        }
        // .setDispatcher does not exist
        this.mockServer.dispatcher = dispatcher
        this.mockServer.start()


    }

    @After
    fun teardown() {
        this.mockServer.shutdown()
    }

    @Test
    fun test() {
        val url = this.mockServer.url("/api/dataset/museus/").toUrl()
        //Aquí falta algo
        Assert.assertEquals(Utils.BODY, this.mockServer.takeRequest().body);
        this.mockServer.shutdown()
    }
}