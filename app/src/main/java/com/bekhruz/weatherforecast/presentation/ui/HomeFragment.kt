package com.bekhruz.weatherforecast.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bekhruz.weatherforecast.presentation.utils.TimeFormattingType.*
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.net.toUri
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionManager
import coil.load
import com.bekhruz.weatherforecast.R
import com.bekhruz.weatherforecast.data.remote.dto.currentweather.asDomain
import com.bekhruz.weatherforecast.data.remote.dto.sixteendayweather.asDomain
import com.bekhruz.weatherforecast.presentation.adapter.HourlyDetailsAdapter
import com.bekhruz.weatherforecast.presentation.adapter.SixteenDayDetailsAdapter
import com.bekhruz.weatherforecast.databinding.FragmentHomeBinding
import com.bekhruz.weatherforecast.domain.models.home.HomeWeatherData
import com.bekhruz.weatherforecast.presentation.core.BaseFragment
import com.bekhruz.weatherforecast.presentation.utils.TimeFormat.getTime
import com.bekhruz.weatherforecast.presentation.viewmodels.WeatherViewModel
import com.bekhruz.weatherforecast.utils.observe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    private val viewModel: WeatherViewModel by activityViewModels()
    private lateinit var hourlyRecyclerView: RecyclerView
    private lateinit var sixteenDayRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getDeviceLocationData()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val hourlyDetailsAdapter = HourlyDetailsAdapter()
        hourlyRecyclerView = binding.hourlyDetailsRecyclerview
        hourlyRecyclerView.adapter = hourlyDetailsAdapter
        hourlyRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        val sevenDayDetailsAdapter = SixteenDayDetailsAdapter()
        sixteenDayRecyclerView = binding.sixteenDayForecastRecyclerview
        sixteenDayRecyclerView.adapter = sevenDayDetailsAdapter
        sixteenDayRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        swipeForSixteenDayForecast()
        viewModel.currentWeatherData.observe(this.viewLifecycleOwner) { weather ->
            binding.apply {
//                currentTemperature.text = weather.current.asDomain().tempC.toString()
//                cityName.text = weather.location.asDomain().name
//                currentStatusImageview.load(
//                    weather.current.asDomain().icon.toUri().buildUpon().scheme("https").build()
//                ) {
//                    //TODO: ADD PLACEHOLDER, ERROR HANDLING FOR COIL
//                }
//                lastUpdatedDate.text =
//                    getTime(
//                        weather.current.asDomain().lastUpdatedEpoch.toLong(),
//                        dateWithWeekday
//                    )
                lastUpdatedDate2.text =
                    getTime(
                        weather.current.asDomain().lastUpdatedEpoch.toLong(),
                        dateWithWeekday
                    )
                currentStatusTextview.text = weather.current.asDomain().text
                windSpeed.text =
                    String.format("%s km/h%nWind", weather.current.asDomain().windKph.toString())
                pressureTextview.text =
                    String.format(
                        "%s mbar%nPressure",
                        weather.current.asDomain().pressureMb.toString()
                    )
                chanceOfRainTextview.text =
                    String.format(
                        "%d%%%nChance of rain",
                        weather.forecastDay[0].asDomain().dailyChanceOfRain
                    )
                humidityTextview.text = String.format("%d%%%nHumidity", weather.current.humidity)
                hourlyDetailsAdapter.submitList(weather.forecastDay[0].asDomain().hour.asDomain())
            }
        }
        viewModel.sixteenDayWeatherData.observe(this.viewLifecycleOwner) { sixteenDay ->
            sevenDayDetailsAdapter.submitList(sixteenDay.data.asDomain())
        }
        binding.icAdd.setOnClickListener {
            goToExploreWeatherFragment()
        }
        binding.icMenu.setOnClickListener {
            goToManageLocationsFragment()
        }

        viewModel.homeWeatherData.observe(viewLifecycleOwner, Observer {

        })

        observe(viewModel.homeWeatherData, ::onHomeWeatherDataLoaded)
    }

    private fun onHomeWeatherDataLoaded(data: HomeWeatherData) {
        binding.currentTemperature.text = data.temperature
        binding.cityName.text = data.cityName
        binding.currentStatusImageview.load(data.imageLink)
        binding.lastUpdatedDate.text = data.lastUpdateDate
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
