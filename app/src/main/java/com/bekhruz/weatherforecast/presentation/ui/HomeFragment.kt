package com.bekhruz.weatherforecast.presentation.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.net.toUri
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionManager
import coil.load
import com.bekhruz.weatherforecast.R
import com.bekhruz.weatherforecast.presentation.adapter.HourlyDetailsAdapter
import com.bekhruz.weatherforecast.presentation.adapter.SixteenDayDetailsAdapter
import com.bekhruz.weatherforecast.databinding.FragmentHomeBinding
import com.bekhruz.weatherforecast.presentation.viewmodels.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private val viewModel: WeatherViewModel by activityViewModels()
    private lateinit var hourlyRecyclerView: RecyclerView
    private lateinit var sixteenDayRecyclerView: RecyclerView
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getDeviceLocationData(requireContext(), requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       /* runBlocking {
            viewModel.getSearchedLocationInfo("london")
            Log.d("EXPLORE LOCATIONS","SEARCHED LOCATION: ${viewModel.searchedLocation.value?.results?.get(0)?.city}")
        }*/
        val hourlyDetailsAdapter = HourlyDetailsAdapter(viewModel)
        hourlyRecyclerView = binding.hourlyDetailsRecyclerview
        hourlyRecyclerView.adapter = hourlyDetailsAdapter
        hourlyRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        val sevenDayDetailsAdapter = SixteenDayDetailsAdapter(viewModel)
        sixteenDayRecyclerView = binding.sixteenDayForecastRecyclerview
        sixteenDayRecyclerView.adapter = sevenDayDetailsAdapter
        sixteenDayRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        swipeForSixteenDayForecast()

        viewModel.currentWeatherData.observe(this.viewLifecycleOwner) { weather ->
            binding.apply {
                currentTemperature.text = weather.current.temp_c.toString()
                cityName.text = weather.location.name
                currentStatusImageview.load(
                    weather.current.condition.icon.toUri().buildUpon().scheme("https").build()
                ) {
                    //TODO: ADD PLACEHOLDER, ERROR HANDLING FOR COIL
                }
                lastUpdatedDate.text =
                    viewModel.getTime(weather.current.last_updated_epoch.toLong(), "date")
                lastUpdatedDate2.text =
                    viewModel.getTime(weather.current.last_updated_epoch.toLong(), "date")
                currentStatusTextview.text = weather.current.condition.text
                windSpeed.text = String.format("%s km/h%nWind", weather.current.wind_kph.toString())
                pressureTextview.text =
                    String.format("%s mbar%nPressure", weather.current.pressure_mb.toString())
                chanceOfRainTextview.text =
                    String.format(
                        "%d%%%nChance of rain",
                        weather.forecast.forecastday[0].day.daily_chance_of_rain
                    )
                humidityTextview.text = String.format("%d%%%nHumidity", weather.current.humidity)
                hourlyDetailsAdapter.submitList(weather.forecast.forecastday[0].hour)
            }
        }
        viewModel.sixteenDayWeatherData.observe(this.viewLifecycleOwner){ sixteenDayData ->
            Log.d(TAG,"${sixteenDayData?.data?.size}")
            sevenDayDetailsAdapter.submitList(sixteenDayData?.data)
        }
        binding.icAdd.setOnClickListener {
            goToExploreWeatherFragment()
        }
        binding.icMenu.setOnClickListener {
            goToManageLocationsFragment()
        }
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
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
    companion object {
        private const val TAG = "HOME FRAGMENT"
    }
}
