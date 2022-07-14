package com.bekhruz.weatherforecast.core

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
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
import com.bekhruz.weatherforecast.presentation.ui.LottieLoaderFragmentDialog
import com.bekhruz.weatherforecast.utils.Inflate
import com.bekhruz.weatherforecast.utils.showDialog

abstract class BaseFragment<VB : ViewBinding>(val inflater: Inflate<VB>) : Fragment() {

    //TODO: LEARN MORE ABOUT BINDING HERE
    private var _binding: VB? = null
    val binding get() = _binding!!
    val bindingSafe get() = _binding
    lateinit var locationPermissionCallback: LocationPermissionInterface
    lateinit var loadingFullScreenDialog: LottieLoaderFragmentDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflater(inflater, container, false)
        loadingFullScreenDialog = LottieLoaderFragmentDialog.getInstance()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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
            when (isGranted) {
                true -> locationPermissionCallback.onLocationGranted()
                false -> {
                    if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                        //denied
                        //show denialDialog
                        showPermissionDeniedDialog()
                    } else {
                        //never ask clicked
                        //goto :( denialFragment and open settings
                        Toast.makeText(requireContext(),"Never Ask Clicked!!!", Toast.LENGTH_LONG).show()
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
            requestPermissionLauncher.launch(
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        }
    }

    interface LocationPermissionInterface {
        fun onLocationGranted()
    }

    private fun showPermissionDeniedDialog() {
        showDialog(
            context = requireContext(),
            title = resources.getString(R.string.attention_dialog_title),
            message = resources.getString(R.string.location_permission_denied),
            //TODO:SHOW SNACKBAR BAR FOR NEGATIVE BUTTON AND SHOW ANOTHER FRAGMENT FOR THIS CASE
            negativeBtnText = "Go to denial fragment",
            negativeBtnAction = {
                Toast.makeText(
                    requireContext(),
                    "denialFragment!!!",
                    Toast.LENGTH_LONG
                ).show()
            },
            positiveBtnText = resources.getString(R.string.accept),
            positiveBtnAction = {
                requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        )
    }

    private fun showAfterPermissionDeniedTwiceDialog() {
        showDialog(
            context = requireContext(),
            title = resources.getString(R.string.attention_dialog_title),
            message = resources.getString(R.string.after_permission_twice),
            //TODO:SHOW SNACKBAR BAR FOR NEGATIVE BUTTON AND SHOW ANOTHER FRAGMENT FOR THIS CASE
            negativeBtnText = resources.getString(R.string.decline),
            negativeBtnAction = {
                Toast.makeText(
                    requireContext(),
                    "Permission is denied Twice",
                    Toast.LENGTH_LONG
                ).show()
                requireActivity().finish()
            },
            positiveBtnText = resources.getString(R.string.accept),
            positiveBtnAction = {
                openSettings()
            }
        )
    }


    open fun controlLoading(shouldLoad: Boolean) {
        if (shouldLoad) {
            loadingFullScreenDialog.show(
                childFragmentManager, LottieLoaderFragmentDialog.TAG
            )
        } else loadingFullScreenDialog.dismiss()
    }

    private fun openSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", requireContext().applicationContext.packageName, null)
        intent.data = uri
        startActivity(intent)
    }
}