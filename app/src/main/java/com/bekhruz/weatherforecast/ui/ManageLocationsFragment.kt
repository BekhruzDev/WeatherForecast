package com.bekhruz.weatherforecast.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bekhruz.weatherforecast.R
import com.bekhruz.weatherforecast.databinding.FragmentManageLocationsBinding
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.icNavigateUpButton.setOnClickListener {
            goBackToHome()
        }
    }

    private fun goBackToHome() {
        findNavController().navigateUp()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}