package com.bekhruz.weatherforecast.core

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.bekhruz.weatherforecast.R
import com.bekhruz.weatherforecast.utils.Inflate
import com.bekhruz.weatherforecast.utils.showDialog
import com.bekhruz.weatherforecast.utils.showSnackMessage
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException
import java.net.UnknownHostException
import javax.inject.Inject

abstract class BaseFragment<VB : ViewBinding>(val inflater: Inflate<VB>) : Fragment(){
    private var _binding: VB? = null
    val binding get() = _binding!!
    val bindingSafe get() = _binding
    lateinit var locationPermissionCallback: LocationPermissionInterface
    lateinit var navigationCallback: NavigationInterface
    var neverAskClicked: Boolean? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflater(inflater, container, false)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    open fun handleError(throwable: Throwable) {
        when (throwable) {
            is IOException -> {
                Toast.makeText(requireContext(), resources.getString(R.string.check_network_state), Toast.LENGTH_LONG ).show()
            }
            else -> {

            }
            //showErrorDialog
        }
    }

    interface LocationPermissionInterface {
        fun onLocationGranted()
    }

    interface NavigationInterface {
        fun navigateToPermissionDeniedFragment(neverAskClicked: Boolean)
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            when (isGranted) {
                true -> locationPermissionCallback.onLocationGranted()
                false -> {
                    if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                        //denied clicked
                        //show denialDialog
                        showPermissionDeniedDialog(false)
                    } else {
                        //never ask clicked
                        //goto :( denialFragment and open settings
                        navigationCallback.navigateToPermissionDeniedFragment(true)
                    }
                }
            }
        }

    fun askLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            locationPermissionCallback.onLocationGranted()
        } else {
            //Directly asking for the Location in the System Dialog
            requestLocationSystemDialog()
        }
    }


    private fun showPermissionDeniedDialog(neverAskClicked: Boolean) {
        showDialog(
            context = requireContext(),
            title = resources.getString(R.string.attention_dialog_title),
            message = resources.getString(R.string.location_permission_denied),
            negativeBtnText = resources.getString(R.string.decline),
            negativeBtnAction = {
                navigationCallback.navigateToPermissionDeniedFragment(neverAskClicked)
            },
            positiveBtnText = resources.getString(R.string.accept),
            positiveBtnAction = { requestLocationSystemDialog() }
        )
    }

    fun openSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", requireContext().applicationContext.packageName, null)
        intent.data = uri
        startActivity(intent)
    }

    open fun requestLocationSystemDialog() {
        requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }
}