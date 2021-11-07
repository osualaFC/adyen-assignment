package com.adyen.android.assignment.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.adyen.android.assignment.data.api.model.mappers.ApiVenueMapper
import com.adyen.android.assignment.domain.model.Venue
import com.adyen.android.assignment.domain.repository.VenueRepository
import com.adyen.android.assignment.presentation.util.Event
import com.adyen.android.assignment.presentation.util.ExceptionHelper
import com.adyen.android.assignment.presentation.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class VenuesViewModel @Inject constructor(
private val repository: VenueRepository,
private val mapper: ApiVenueMapper
) : ViewModel() {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    val venuesLiveData: LiveData<Event<Resource<List<Venue>>>> get() = _venuesLiveData
    private val _venuesLiveData = MutableLiveData<Event<Resource<List<Venue>>>>()

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
                    _venuesLiveData.postValue(Event(Resource.success(venues)))
                },{
                    val exception = ExceptionHelper.getException(it)
                    _venuesLiveData.postValue(Event(Resource.error(exception)))
                })
        )
    }

    private fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    private fun clearDisposables() {
        compositeDisposable.clear()
    }

    override fun onCleared() {
        clearDisposables()
    }

}