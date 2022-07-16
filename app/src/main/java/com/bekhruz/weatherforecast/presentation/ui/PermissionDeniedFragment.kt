package com.bekhruz.weatherforecast.presentation.ui

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.bekhruz.weatherforecast.R
import com.bekhruz.weatherforecast.core.BaseFragment
import com.bekhruz.weatherforecast.databinding.FragmentPermissionDeniedBinding

class PermissionDeniedFragment :
    BaseFragment<FragmentPermissionDeniedBinding>(FragmentPermissionDeniedBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val neverAskClicked = arguments?.getBoolean("neverAskClicked")!!

        binding.allowPermissionButton.text = if (neverAskClicked) {
            resources.getString(R.string.open_settings)
        } else resources.getString(R.string.allow)

        if (neverAskClicked) {
            binding.allowPermissionButton.setOnClickListener {
                openSettings()
                requireActivity().finish()
            }
        } else binding.allowPermissionButton.setOnClickListener {
            findNavController().navigate(R.id.action_permissionDeniedFragment_to_homeFragment)
        }
        binding.exitAppButton.setOnClickListener {
            requireActivity().finish()
        }
    }
}