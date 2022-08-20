package com.bekhruz.weatherforecast.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.transition.TransitionInflater
import com.bekhruz.weatherforecast.R
import com.bekhruz.weatherforecast.databinding.FragmentManageLocationsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ManageLocationsFragment : Fragment() {
    private var _binding: FragmentManageLocationsBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentManageLocationsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    /* override fun onCreate(savedInstanceState: Bundle?) {
         super.onCreate(savedInstanceState)
         val inflater = TransitionInflater.from(requireContext())
         enterTransition = inflater.inflateTransition(R.transition.slide_left)

     }*/
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.icNavigateUpButton.setOnClickListener {
            goBackToHome()
        }
    }

    private fun goBackToHome() {
        val currentNavController = findNavController()
        if (currentNavController.currentDestination?.id == R.id.manageLocationsFragment)
            currentNavController.navigateUp()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}