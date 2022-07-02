package com.bekhruz.weatherforecast.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bekhruz.weatherforecast.presentation.adapter.SearchedLocationsAdapter
import com.bekhruz.weatherforecast.databinding.FragmentExploreWeatherBinding
import com.bekhruz.weatherforecast.presentation.viewmodels.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExploreWeatherFragment : Fragment(), SearchView.OnQueryTextListener {
    private val viewModel: WeatherViewModel by activityViewModels()
    private var _binding: FragmentExploreWeatherBinding? = null
    private val binding get() = _binding!!
    private lateinit var searchedLocationsRecyclerView: RecyclerView
    private lateinit var searchedLocationsAdapter: SearchedLocationsAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentExploreWeatherBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchedLocationsRecyclerView = binding.searchedLocationsRecyclerview
        searchedLocationsAdapter = SearchedLocationsAdapter { }
        searchedLocationsRecyclerView.adapter = searchedLocationsAdapter
        searchedLocationsRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.locationsSearchview.setOnQueryTextListener(this)
        binding.locationsSearchview.isSubmitButtonEnabled = true

        binding.icNavigateUpButton.setOnClickListener {
            goBackHome()
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        binding.locationsSearchview.findViewById<View>(androidx.appcompat.R.id.search_close_btn).visibility = View.GONE
        binding.locationsSearchview.findViewById<View>(androidx.appcompat.R.id.search_go_btn).visibility = View.GONE
        if (query != null) {
            searchRemoteData(query)
        }
        binding.locationsSearchview.clearFocus()
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        binding.locationsSearchview.findViewById<View>(androidx.appcompat.R.id.search_close_btn).visibility = View.GONE
        binding.locationsSearchview.findViewById<View>(androidx.appcompat.R.id.search_go_btn).visibility = View.GONE
        if (newText != null) {
            searchRemoteData(newText)
        }
        return true
    }

    private fun searchRemoteData(queryLocation: String) {
        viewModel.getSearchedLocationInfo(queryLocation)
            .observe(this.viewLifecycleOwner) { location ->
                location.let { searchedLocationsAdapter.submitList(it.results) }
            }
    }

    private fun goBackHome() {
        findNavController().navigateUp()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}