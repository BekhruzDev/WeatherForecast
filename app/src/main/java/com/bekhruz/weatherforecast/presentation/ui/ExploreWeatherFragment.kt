package com.bekhruz.weatherforecast.presentation.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionInflater
import com.bekhruz.weatherforecast.R
import com.bekhruz.weatherforecast.core.BaseFragment
import com.bekhruz.weatherforecast.databinding.FragmentExploreWeatherBinding
import com.bekhruz.weatherforecast.domain.models.geocoding.LocationResult
import com.bekhruz.weatherforecast.presentation.adapter.SearchedLocationsAdapter
import com.bekhruz.weatherforecast.presentation.utils.RecyclerViewItemClick
import com.bekhruz.weatherforecast.presentation.viewmodels.WeatherViewModel
import com.bekhruz.weatherforecast.utils.observe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExploreWeatherFragment :
    BaseFragment<FragmentExploreWeatherBinding>(FragmentExploreWeatherBinding::inflate),
    RecyclerViewItemClick<LocationResult>,
    SearchView.OnQueryTextListener {
    private val viewModel: WeatherViewModel by activityViewModels()
    private lateinit var searchedLocationsRecyclerView: RecyclerView
    private lateinit var searchedLocationsAdapter: SearchedLocationsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflater = TransitionInflater.from(requireContext())
        enterTransition = inflater.inflateTransition(R.transition.slide_right)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchedLocationsRecyclerView = binding.searchedLocationsRecyclerview
        searchedLocationsAdapter = SearchedLocationsAdapter(this)
        searchedLocationsRecyclerView.adapter = searchedLocationsAdapter
        searchedLocationsRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.locationsSearchview.setOnQueryTextListener(this)
        binding.locationsSearchview.isSubmitButtonEnabled = true
        binding.icNavigateUpButton.setOnClickListener {
            goBackHome()
        }

        observe(viewModel.errorOther, ::handleError)
        observe(viewModel.loading, ::showLoader)
    }

    override fun onResume() {
        if (binding.locationsSearchview.requestFocus()) {
            (requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).toggleSoftInput(
                InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY
            )
        }
        super.onResume()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        binding.locationsSearchview.findViewById<View>(androidx.appcompat.R.id.search_close_btn).visibility =
            View.GONE
        binding.locationsSearchview.findViewById<View>(androidx.appcompat.R.id.search_go_btn).visibility =
            View.GONE
        if (query != null && query.isNotEmpty() && query.isNotBlank()) {
            searchRemoteData(query)
        }
        binding.locationsSearchview.clearFocus()
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        binding.locationsSearchview.findViewById<View>(androidx.appcompat.R.id.search_close_btn).visibility =
            View.GONE
        binding.locationsSearchview.findViewById<View>(androidx.appcompat.R.id.search_go_btn).visibility =
            View.GONE
        if (newText != null && newText.isNotEmpty() && newText.isNotBlank()) {
            searchRemoteData(newText)
        }
        return true
    }

    private fun searchRemoteData(queryLocation: String) {
        viewModel.getSearchedLocationData(queryLocation)
            .observe(this.viewLifecycleOwner) { location ->
                location.let { searchedLocationsAdapter.submitList(it.locationResults) }
            }
    }

    override fun onItemClicked(item: LocationResult) {
        goBackHome()
        viewModel.applySelectedLocationWeatherData(item)
    }

    private fun showLoader(isLoading: Boolean) {
        if (isLoading) {
            binding.shimmerRoot.root.startShimmer()
            binding.shimmerRoot.root.visibility = View.VISIBLE
            binding.searchedLocationsRecyclerview.visibility = View.GONE
        } else {
            hideLoader()
        }
    }

    private fun hideLoader() {
        binding.shimmerRoot.root.stopShimmer()
        binding.shimmerRoot.root.visibility = View.GONE
        binding.searchedLocationsRecyclerview.visibility = View.VISIBLE
    }

    private fun goBackHome() {
        val currentNavController = findNavController()
        if (currentNavController.currentDestination?.id == R.id.exploreWeatherFragment) {
            currentNavController.navigateUp()
        }
    }
}