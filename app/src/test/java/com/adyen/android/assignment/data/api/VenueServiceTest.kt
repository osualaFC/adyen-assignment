package com.adyen.android.assignment.data.api

import com.adyen.android.assignment.DependencyProvider
import com.adyen.android.assignment.RxTrampolineSchedulerRule
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert
import org.junit.Test
import org.junit.After
import org.junit.Before
import org.junit.Rule
import java.io.IOException

class VenueServiceTest {

    private lateinit var service: VenueService
    private lateinit var server: MockWebServer

    @Rule
    @JvmField
    var testSchedulerRule = RxTrampolineSchedulerRule()

    @Before
    fun init(){
        server = MockWebServer()
        service = DependencyProvider
            .getRetrofit(server.url("/"))
            .create(VenueService::class.java)
    }

    @After
    @Throws(IOException::class)
    fun tearDown() {
        server.shutdown()
    }

    @Test
    fun getVenuesWithLatitudeAndLongitude_returns200() {
        queueResponse {
            setResponseCode(200)
            setBody(DependencyProvider.getResponseFromJson("explore-response"))
        }
        val request = VenueRecommendationsQueryBuilder()
            .setLatitudeLongitude(40.724216906965616, -73.9896507407283)
            .build()
        service.getVenues(request).blockingGet().let {
           Assert.assertNotNull(it.response)
        }
    }

    @Test
    fun getVenuesWithoutLatitudeAndLongitude_returns400() {
        queueResponse {
            setResponseCode(400)
            setBody(DependencyProvider.getResponseFromJson("explore-response"))
        }
        val request = VenueRecommendationsQueryBuilder()
            .build()
        service.getVenues(request).doOnError {
            Assert.assertNotNull(it)
        }
    }

    private fun queueResponse(block: MockResponse.() -> Unit) {
        server.enqueue(MockResponse().apply(block))
    }
}