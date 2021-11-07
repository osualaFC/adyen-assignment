package com.adyen.android.assignment.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.adyen.android.assignment.DummyData
import com.adyen.android.assignment.LiveDataTestUtil
import com.adyen.android.assignment.RxTrampolineSchedulerRule
import com.adyen.android.assignment.data.api.model.mappers.ApiVenueMapper
import com.adyen.android.assignment.domain.repository.VenueRepository
import com.adyen.android.assignment.presentation.util.Status
import com.nhaarman.mockito_kotlin.*
import io.reactivex.Single
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.IOException

class VenuesViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    var testSchedulerRule = RxTrampolineSchedulerRule()

    private val testData = DummyData
    private val repository: VenueRepository = mock()
    private val mapper = ApiVenueMapper()
    private lateinit var viewModel: VenuesViewModel

    @Before
    @Throws(IOException::class)
    fun setUp() {
        viewModel = VenuesViewModel(repository, mapper)
    }

    @Test
    fun getVenues_returnsStatusSuccess() {
        whenever(repository.getVenues(40.724216906965616, -73.9896507407283))
            .thenReturn(Single.just(testData.venueResponse))
        viewModel.getVenues(40.724216906965616, -73.9896507407283)
        verify(repository, times(1)).getVenues(40.724216906965616, -73.9896507407283)
        assertEquals(Status.SUCCESS, LiveDataTestUtil.getValue(viewModel.venuesLiveData)?.getContentIfNotHandled()?.status)
    }

}