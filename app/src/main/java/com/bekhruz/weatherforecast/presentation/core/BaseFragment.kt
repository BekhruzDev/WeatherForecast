package com.bekhruz.weatherforecast.presentation.core

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaPlayer
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
import com.bekhruz.weatherforecast.utils.Inflate


abstract class BaseFragment<VB : ViewBinding>(val inflater: Inflate<VB>) : Fragment(){

    private var _binding: VB? = null
    val binding get() = _binding!!
    val bindingSafe get() = _binding

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



    @RequiresApi(api = Build.VERSION_CODES.M)
    private fun setSystemBarTheme(isStatusBarFontDark: Boolean) {
        // Fetch the current flags.
        val lFlags = requireActivity().window.decorView.systemUiVisibility
        // Update the SystemUiVisibility depending on whether we want a Light or Dark theme.
        requireActivity().window.decorView.systemUiVisibility =
            if (isStatusBarFontDark) lFlags and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv() else lFlags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }


    open fun handleError(throwable: Throwable) {
        when (throwable) {
            //showErrorDialog
        }
    }

    private var beforeClickPermissionRat = false
    private var afterClickPermissionRat = false
    private val CODE_PERMISSION_CALL_PHONE: Int = 1021
    val requestPhonePermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { result: MutableMap<String, Boolean> ->
            val deniedList: List<String> = result.filter {
                !it.value
            }.map {
                it.key
            }

            when {
                deniedList.isNotEmpty() -> {
                    val map = deniedList.groupBy { permission ->
                        if (shouldShowRequestPermissionRationale(permission)) PermissionActions.DENIED else PermissionActions.EXPLAINED
                    }
                    map[PermissionActions.DENIED]?.let {
//
                    }
                    map[PermissionActions.EXPLAINED]?.let {
                        //request denied ,send to settings

                    }

                }
                else -> {
                    requestCallAction(isCallActionWithUssd)
                }
            }
        }

    private var isCallActionWithUssd = false
    open fun requestCallAction(isCallActionWithUssd: Boolean = false) {
        this.isCallActionWithUssd = isCallActionWithUssd
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CALL_PHONE
            ) == PackageManager.PERMISSION_GRANTED -> {
                val encodedHash = Uri.encode("#")
                val ussd = if (isCallActionWithUssd) "*107$encodedHash" else "712051548"
                val intent = Intent("android.intent.action.CALL", Uri.parse("tel:$ussd"))
                try {
                    startActivity(intent)
                } catch (e: SecurityException) {
                    e.printStackTrace()
                }
            }
            shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE) -> {

            }

            else -> {
                // You can directly ask for the permission.
                requestPhonePermissionLauncher.launch(arrayOf(Manifest.permission.CALL_PHONE))
            }
        }


    }

    private fun openSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", requireContext().applicationContext.packageName, null)
        intent.data = uri
        startActivityForResult(intent, CODE_PERMISSION_CALL_PHONE)
    }

    enum class PermissionActions {
        DENIED,
        EXPLAINED
    }


}