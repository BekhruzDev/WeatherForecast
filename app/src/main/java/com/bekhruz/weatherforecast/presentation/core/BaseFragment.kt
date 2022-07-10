package com.bekhruz.weatherforecast.presentation.core

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.bekhruz.weatherforecast.R
import com.bekhruz.weatherforecast.presentation.core.BaseFragment.LocationPermissionInterface
import com.bekhruz.weatherforecast.utils.Inflate
import com.bekhruz.weatherforecast.utils.viewExt.showDialog
import dagger.hilt.android.AndroidEntryPoint

abstract class BaseFragment<VB : ViewBinding>(val inflater: Inflate<VB>) : Fragment() {

    //TODO: LEARN MORE ABOUT BINDING HERE
    private var _binding: VB? = null
    val binding get() = _binding!!
    val bindingSafe get() = _binding
    lateinit var locationPermissionCallback: LocationPermissionInterface

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

    //TODO: ASK THIS!
    @RequiresApi(api = Build.VERSION_CODES.M)
    private fun setSystemBarTheme(isStatusBarFontDark: Boolean) {
        // Fetch the current flags.
        val lFlags = requireActivity().window.decorView.systemUiVisibility
        // Update the SystemUiVisibility depending on whether we want a Light or Dark theme.
        requireActivity().window.decorView.systemUiVisibility =
            if (isStatusBarFontDark) lFlags and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv() else lFlags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }
    //TODO: LEARN THIS!
    open fun handleError(throwable: Throwable) {
        when (throwable) {
            //showErrorDialog
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                locationPermissionCallback.onLocationGranted()
            } else {
                //Permission was denied
                showPermissionDeniedDialog()
                //TODO:showInContextUI(...) explaining the user that the location should be granted
            }
        }

    fun askLocationPermission() {
        when {
                ContextCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED -> {
                    locationPermissionCallback.onLocationGranted()
                }
                shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) -> {
                    showPermissionDeniedDialog()
                }
                else -> {
                    //Directly asking for the Location in the System Dialog
                    requestPermissionLauncher.launch(
                        Manifest.permission.ACCESS_FINE_LOCATION
                    )
                }
        }
    }

    interface LocationPermissionInterface {
        fun onLocationGranted()
    }

    private fun showPermissionDeniedDialog(){
        return showDialog(
            context = requireContext(),
            title = resources.getString(R.string.attention_dialog_title),
            message = resources.getString(R.string.location_permission_denied),
            //negativeBtnText = resources.getString(R.string.decline),
            positiveBtnText = resources.getString(R.string.accept),
            positiveBtnAction = openSettings()
        )}
    private fun openSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", requireContext().applicationContext.packageName, null)
        intent.data = uri
        startActivity(intent)
    }
}