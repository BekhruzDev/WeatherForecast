package com.bekhruz.weatherforecast.presentation.ui

import android.os.Bundle
import android.view.View
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.net.toUri
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionManager
import coil.load
import com.bekhruz.weatherforecast.R
import com.bekhruz.weatherforecast.data.remote.utils.Constants.HTTPS
import com.bekhruz.weatherforecast.databinding.FragmentHomeBinding
import com.bekhruz.weatherforecast.presentation.adapter.HourlyDetailsAdapter
import com.bekhruz.weatherforecast.presentation.adapter.SixteenDayDetailsAdapter
import com.bekhruz.weatherforecast.domain.models.currentweather.CurrentWeatherData
import com.bekhruz.weatherforecast.domain.models.sixteendayweather.SixteenDayData
import com.bekhruz.weatherforecast.core.BaseFragment
import com.bekhruz.weatherforecast.presentation.viewmodels.WeatherViewModel
import com.bekhruz.weatherforecast.utils.observe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate), BaseFragment.LocationPermissionInterface {
    private val viewModel: WeatherViewModel by activityViewModels()
    private lateinit var hourlyRecyclerView: RecyclerView
    private lateinit var hourlyDetailsAdapter: HourlyDetailsAdapter
    private lateinit var sixteenDayRecyclerView: RecyclerView
    private lateinit var sixteenDayDetailsAdapter: SixteenDayDetailsAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //before asking for the location, set locationPermissionCallback to HomeFragment
        //on which we perform onLocationCallback() that triggers the viewModel's
        //getDeviceLocation()
        locationPermissionCallback = this

        hourlyRecyclerView = binding.hourlyDetailsRecyclerview
        hourlyDetailsAdapter = HourlyDetailsAdapter()
        hourlyRecyclerView.adapter = hourlyDetailsAdapter
        hourlyRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        sixteenDayRecyclerView = binding.sixteenDayForecastRecyclerview
        sixteenDayDetailsAdapter = SixteenDayDetailsAdapter()
        sixteenDayRecyclerView.adapter = sixteenDayDetailsAdapter
        sixteenDayRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)


        binding.icAdd.setOnClickListener {
            goToExploreWeatherFragment()
        }
        binding.icMenu.setOnClickListener {
            goToManageLocationsFragment()
        }

        observe(viewModel.currentWeatherData, ::onCurrentWeatherDataLoaded)
        observe(viewModel.sixteenDayWeatherData, ::onSixteenDayWeatherDataLoaded)
        observe(viewModel.loading, ::controlLoading)
        swipeForSixteenDayForecast()
    }

    override fun onStart() {
        askLocationPermission()
        super.onStart()
    }

    private fun onCurrentWeatherDataLoaded(data: CurrentWeatherData) {
        binding.apply{
            currentTemperature.text = data.tempC
            cityName.text = data.name
            currentStatusImageview.load(data.icon.toUri().buildUpon().scheme(HTTPS).build())
            lastUpdatedDate.text = data.lastUpdateDate
            lastUpdatedDate2.text = data.lastUpdateDate
            humidityTextview.text = data.humidity
            chanceOfRainTextview.text = data.dailyChanceOfRain
            pressureTextview.text = data.pressureMb
            windSpeed.text = data.windKph
            currentStatusTextview.text = data.text
        }
        hourlyDetailsAdapter.submitList(data.hourlyData)
    }

    private fun onSixteenDayWeatherDataLoaded(data: SixteenDayData){
        sixteenDayDetailsAdapter.submitList(data.dailyForecasts)
    }



    private fun swipeForSixteenDayForecast() {
        var set = false
        val startingConstraintSet = ConstraintSet()
        startingConstraintSet.clone(binding.root)
        val finishingConstraintSet = ConstraintSet()
        finishingConstraintSet.clone(requireContext(), R.layout.fragment_home_v2)
        binding.sixteenDayForecastTextview.setOnClickListener {
            TransitionManager.beginDelayedTransition(binding.root)
            val constraintSet = if (set) startingConstraintSet else finishingConstraintSet
            constraintSet.applyTo(binding.root)
            set = !set
        }
    }



    override fun onLocationGranted() {
            viewModel.applyDeviceLocationWeatherData()
    }

    private fun goToManageLocationsFragment() {
        findNavController().navigate(
            R.id.action_homeFragment_to_manageLocationsFragment
        )
    }

    private fun goToExploreWeatherFragment() {
        findNavController().navigate(
            R.id.action_homeFragment_to_exploreWeatherFragment
        )
    }

}
