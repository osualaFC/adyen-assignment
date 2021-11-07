package com.adyen.android.assignment.domain.repository

import com.adyen.android.assignment.DummyData
import com.adyen.android.assignment.RxTrampolineSchedulerRule
import com.adyen.android.assignment.data.api.VenueRecommendationsQueryBuilder
import com.adyen.android.assignment.data.api.VenueService
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.IOException

class VenueRepositoryTest {

    @Rule
    @JvmField
    var testSchedulerRule = RxTrampolineSchedulerRule()

    private val testData = DummyData
    private val service: VenueService = mock()
    private lateinit var repository: VenueRepository

    @Before
    @Throws(IOException::class)
    fun setUp() {
        repository = VenueRepository(service)
    }

    @Test
    fun getVenue_returns_venueRecommendationResponse() {
        val request = VenueRecommendationsQueryBuilder()
            .setLatitudeLongitude(40.724216906965616, -73.9896507407283)
            .build()
        whenever(service.getVenues(request)).thenReturn(Single.just(testData.venueResponse))

        val responseData = repository.getVenues(40.724216906965616, -73.9896507407283).blockingGet()

        verify(service, times(1)).getVenues(request)
        assertEquals(testData.venueResponse.response, responseData.response)
    }

}