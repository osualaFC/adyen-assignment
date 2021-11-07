package com.adyen.android.assignment.presentation.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.adyen.android.assignment.databinding.FragmentVenuesBinding
import com.adyen.android.assignment.domain.model.NetworkException
import com.adyen.android.assignment.domain.model.NetworkUnavailableException
import com.adyen.android.assignment.domain.model.Venue
import com.adyen.android.assignment.domain.model.VenueNotFoundException
import com.adyen.android.assignment.presentation.adapter.VenueAdapter
import com.adyen.android.assignment.presentation.util.EventObserver
import com.adyen.android.assignment.presentation.util.Extension.gone
import com.adyen.android.assignment.presentation.util.Extension.visible
import com.adyen.android.assignment.presentation.util.Status
import com.adyen.android.assignment.presentation.viewmodel.VenuesViewModel
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint

const val REQUEST_CODE_LOCATION_PERMISSION = 1

@AndroidEntryPoint
class VenuesFragment : Fragment() {

    private val binding get() = _binding!!
    private var _binding: FragmentVenuesBinding? = null
    private val viewModel: VenuesViewModel by viewModels()

    private val venuesAdapter by lazy { VenueAdapter(requireContext()) }
    private val fusedLocationClient by lazy{
        LocationServices.getFusedLocationProviderClient(requireContext())
    }
    private val locationRequest by lazy{ LocationRequest() }
    private lateinit var locationCallback: LocationCallback

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVenuesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getLocationAccess()
        getAndObserveLivedata()
        binding.errorViews.btnTryAgain.setOnClickListener {
           getAndObserveLivedata()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == REQUEST_CODE_LOCATION_PERMISSION ) {
            if (grantResults.contains(PackageManager.PERMISSION_GRANTED)) {
                getLocationAccess()
            } else {
                Toast.makeText(requireContext(), "permission not granted", Toast.LENGTH_LONG).show()
                requireActivity().finish()
            }
        }
    }

    private fun getAndObserveLivedata() {
        viewModel.venuesLiveData.observe(viewLifecycleOwner, EventObserver { result ->
            result.let{
                when(result.status) {
                    Status.LOADING -> {
                        binding.lottieLocation.visible()
                    }
                    Status.SUCCESS -> {
                        binding.lottieLocation.gone()
                         setupRecyclerView(result.data)
                    }

                    Status.ERROR -> {
                        binding.lottieLocation.gone()
                        handleError(result.failure)
                    }
                }
            }
        })
    }

    private fun handleError(failure: Throwable?) {
          binding.errorViews.root.visible()
        when (failure) {
            is NetworkException -> {
              binding.errorViews.networkError.visible()
            }
            is NetworkUnavailableException -> {
                binding.errorViews.noInternet.visible()
            }
            is VenueNotFoundException -> {
                binding.errorViews.noVenues.visible()
            }
        }
    }

    private fun setupRecyclerView(list : List<Venue>?) {
        if(!list.isNullOrEmpty())
            binding.rv.apply {
                adapter = venuesAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
                venuesAdapter.submitList(list)
            }
    }

    private fun getLocationAccess() {
        if (ContextCompat.checkSelfPermission(requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getLocationUpdates()
            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                null
            )
        } else
            ActivityCompat.requestPermissions(requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_CODE_LOCATION_PERMISSION)
    }

    private fun getLocationUpdates() {
        locationRequest.interval = 10000000
        locationRequest.fastestInterval = 10000000
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                if (locationResult.locations.isNotEmpty()) {
                    val location = locationResult.lastLocation
                    if (location != null) {
                        with(binding) {
                            lottieLocation.gone()
                        }
                        viewModel.getVenues(location.latitude, location.longitude)
                    }
                    else binding.lottieLocation.visible()
                }
            }
        }
    }

}