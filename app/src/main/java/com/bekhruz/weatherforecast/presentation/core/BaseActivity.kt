package com.bekhruz.weatherforecast.presentation.core

import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.Observer
import androidx.viewbinding.ViewBinding
import com.bekhruz.weatherforecast.MainActivity

abstract class BaseActivity<VB : ViewBinding>(val inflater: (LayoutInflater) -> VB) :
    AppCompatActivity() {

    lateinit var binding: VB
    val bindingSafe: VB?
        get() = if (isBindingInitialized()) binding else null

    fun isBindingInitialized(): Boolean = ::binding.isInitialized
    fun isBindingNotInitialized(): Boolean = !(::binding.isInitialized)

    lateinit var networkHandlerObserver: Observer<Throwable>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflater(layoutInflater)
        setContentView(binding.root)
    }

    fun setStatusBarColor(color: Int) {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );
        when (color) {
            Color.TRANSPARENT -> makeStatusBarTransparent()
            else -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                    window.statusBarColor = ResourcesCompat.getColor(this.resources, color, null)

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (isNightTheme()) window.decorView.systemUiVisibility =
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    else window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                }
            }
        }
    }

    private fun makeStatusBarTransparent() {
        window.statusBarColor = Color.TRANSPARENT
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (isNightTheme()) window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            else window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        } else {
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
    }

    fun isNightTheme(): Boolean {
        val mode = this.resources.configuration.uiMode.and(Configuration.UI_MODE_NIGHT_MASK)
        when (mode) {
            Configuration.UI_MODE_NIGHT_NO -> {
                return false
            }
            Configuration.UI_MODE_NIGHT_YES -> {
                return true
            }
            Configuration.UI_MODE_NIGHT_UNDEFINED -> {
                return true
            }
        }
        return true
    }

    private fun reopenActivity() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }


}