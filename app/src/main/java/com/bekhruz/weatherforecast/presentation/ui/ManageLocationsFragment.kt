package com.bekhruz.weatherforecast.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.transition.TransitionInflater
import com.bekhruz.weatherforecast.R
import com.bekhruz.weatherforecast.core.BaseFragment
import com.bekhruz.weatherforecast.databinding.FragmentManageLocationsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ManageLocationsFragment : BaseFragment<FragmentManageLocationsBinding>(FragmentManageLocationsBinding::inflate) {

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
}