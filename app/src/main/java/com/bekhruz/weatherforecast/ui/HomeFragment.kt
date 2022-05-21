package com.bekhruz.weatherforecast.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import androidx.core.net.toUri
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bekhruz.weatherforecast.R
import com.bekhruz.weatherforecast.adapter.HourlyDetailsAdapter
import com.bekhruz.weatherforecast.databinding.FragmentHomeBinding
import com.bekhruz.weatherforecast.viewmodels.WeatherViewModel

class HomeFragment : Fragment() {
    //TODO:Work on the hourly weather data
    private val viewModel: WeatherViewModel by activityViewModels()
    private lateinit var hourlyRecyclerView:RecyclerView
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
        val hourlyDetailsAdapter = HourlyDetailsAdapter(viewModel)
        hourlyDetailsAdapter.submitList(viewModel.weather.value?.forecast?.forecastday?.get(0)?.hour)
        hourlyRecyclerView = binding.hourlyDetailsRecyclerview
        hourlyRecyclerView.adapter = hourlyDetailsAdapter
        hourlyRecyclerView.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        binding.icPlus.setOnClickListener {
            goToManageLocationsFragment()
        }

        viewModel.weather.observe(this.viewLifecycleOwner) { weather ->
            binding.apply {
                currentTemperature.text = weather.current.temp_c.toString()
                cityName.text = weather.location.name
                currentStatusImageview.load(weather.current.condition.icon.toUri().buildUpon().scheme("https").build()){
                    //TODO: ADD PLACEHOLDER, errorHandling FOR COIL
                    Log.d("HOMEFRAGMENT", "ICON ISNT LOADED!!!")
                }
                lastUpdatedDate.text = viewModel.getDate(weather.current.last_updated_epoch.toLong())
                currentStatusTextview.text = weather.current.condition.text
                windSpeed.text = String.format("%s km/h%nWind", weather.current.wind_kph.toString())
                pressureTextview.text = String.format("%s mbar%nPressure", weather.current.pressure_mb.toString())
                chanceOfRainTextview.text =
                    String.format("%d%%%nChance of rain",weather.forecast.forecastday[0].day.daily_chance_of_rain)
                humidityTextview.text = String.format("%d%%%nHumidity", weather.current.humidity)
            }
        }
        /* runBlocking{
             val current = Repositories.getSevenDayWeather("london").body()!!.current.temp_c
             Log.d("Weather in London", "Currently $current ")
         }*/
    }

    private fun goToManageLocationsFragment() {
        findNavController().navigate(
            R.id.action_homeFragment_to_manageLocationsFragment
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
