package com.bekhruz.weatherforecast.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bekhruz.weatherforecast.R
import com.bekhruz.weatherforecast.core.BaseFragment
import com.bekhruz.weatherforecast.databinding.FragmentPermissionDeniedBinding

class PermissionDeniedFragment : BaseFragment<FragmentPermissionDeniedBinding>(FragmentPermissionDeniedBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.allowPermissionButton.setOnClickListener {
            //TODO:handle both cases: 1. go to settings. 2. show sysDialog
        }
        binding.exitAppButton.setOnClickListener {
            requireActivity().finish()
        }
    }
    companion object {
        fun getInstance() = PermissionDeniedFragment()
    }
}