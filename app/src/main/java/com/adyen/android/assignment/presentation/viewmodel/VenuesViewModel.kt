package com.adyen.android.assignment.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.adyen.android.assignment.base.BaseViewModel
import com.adyen.android.assignment.data.api.model.mappers.ApiVenueMapper
import com.adyen.android.assignment.domain.model.Venue
import com.adyen.android.assignment.domain.repository.VenueRepository
import com.adyen.android.assignment.presentation.util.Event
import com.adyen.android.assignment.presentation.util.ExceptionHelper
import com.adyen.android.assignment.presentation.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class VenuesViewModel @Inject constructor(
private val repository: VenueRepository,
private val mapper: ApiVenueMapper
) : BaseViewModel() {

    val venuesLiveData: LiveData<Event<Resource<List<Venue>>>> get() = _venuesLiveData
    private val _venuesLiveData = MutableLiveData<Event<Resource<List<Venue>>>>()

    var latitude : Double? = null
    var longitude : Double? = null

    fun getVenues(latitude : Double,longitude : Double ) {
        addDisposable(
            repository.getVenues(latitude, longitude)
                .doOnSubscribe{
                    _venuesLiveData.postValue(Event(Resource.loading()))
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({venueResponse ->
                    val venues = mutableListOf<Venue>()
                    venueResponse.response.groups.forEach { venueRecommendationGroup ->
                        venues+=   venueRecommendationGroup.items.map { recommendedItem ->
                            mapper.mapToDomain(recommendedItem)
                        }}
                    Log.i("TAG", "getVenues: $venues")
                    _venuesLiveData.postValue(Event(Resource.success(venues)))
                },{
                    val exception = ExceptionHelper.getException(it)
                    _venuesLiveData.postValue(Event(Resource.error(exception)))
                })
        )
    }



}