package com.bekhruz.weatherforecast.presentation.ui

import android.os.Bundle
import android.view.View
import androidx.core.net.toUri
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate),
    BaseFragment.LocationPermissionInterface,
    BaseFragment.NavigationInterface {
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
        navigationCallback = this

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


        BottomSheetBehavior.from(binding.sixteenDayBottomSheet).apply {
            lifecycleScope.launchWhenStarted {
                delay(500)
                peekHeight =
                    binding.root.measuredHeight - (binding.mainCard.measuredHeight + binding.hourlyDetailsCard.measuredHeight)
            }
            state = BottomSheetBehavior.STATE_COLLAPSED
        }
        observe(viewModel.currentWeatherData, ::onCurrentWeatherDataLoaded)
        observe(viewModel.sixteenDayWeatherData, ::onSixteenDayWeatherDataLoaded)
        observe(viewModel.errorOther, ::handleError)
        observe(viewModel.loading, ::showLoader)
    }

    override fun onStart() {
        askLocationPermission()
        super.onStart()
    }

    override fun onPause() {
        hideLoader()
        super.onPause()
    }

    private fun onCurrentWeatherDataLoaded(data: CurrentWeatherData) {
        binding.apply {
            currentTemperature.text = data.tempC
            cityName.text = data.name
            currentStatusImageview.load(data.icon.toUri().buildUpon().scheme(HTTPS).build())
            lastUpdatedDate1.text = data.lastUpdatedTime
            lastUpdatedDate2.text = data.lastUpdateDate
            humidityTextview.text = data.humidity
            chanceOfRainTextview.text = data.dailyChanceOfRain
            pressureTextview.text = data.pressureMb
            windSpeed.text = data.windKph
            currentStatusTextview.text = data.text
        }
        hourlyDetailsAdapter.submitList(data.hourlyData)
    }

    private fun onSixteenDayWeatherDataLoaded(data: SixteenDayData) {
        sixteenDayDetailsAdapter.submitList(data.dailyForecasts)
    }


    private fun showLoader(isLoading: Boolean) {
        if (isLoading) {
            binding.shimmerRoot.root.startShimmer()
            binding.shimmerRoot.root.visibility = View.VISIBLE
            binding.root.visibility = View.GONE
        } else {
            hideLoader()
        }
    }

    private fun hideLoader() {
        binding.shimmerRoot.root.stopShimmer()
        binding.shimmerRoot.root.visibility = View.GONE
        binding.root.visibility = View.VISIBLE
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

    override fun navigateToPermissionDeniedFragment(neverAskClicked: Boolean) {
        findNavController().navigate(
            R.id.action_homeFragment_to_permissionDeniedFragment,
            bundleOf("neverAskClicked" to neverAskClicked)
        )
    }

    private fun Int.toPixels(): Int {
        val metrics = resources.displayMetrics
        val densityDpi = metrics.density.toInt()
        return this * densityDpi / 160
    }

    companion object {
        fun getInstance() = HomeFragment()
    }
}
