package com.bekhruz.weatherforecast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.bekhruz.weatherforecast.databinding.ActivityMainBinding
import com.bekhruz.weatherforecast.presentation.ui.HomeFragment
import com.bekhruz.weatherforecast.presentation.viewmodels.WeatherViewModel
import com.bekhruz.weatherforecast.utils.observe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var networkChangedReceiver: NetworkChangedReceiver
    private val viewModel: WeatherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        networkChangedReceiver = NetworkChangedReceiver()
        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(networkChangedReceiver, filter)
        observe(viewModel.hasInternetLiveData, ::handleNetworkConnectivity)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(networkChangedReceiver)
    }

    inner class NetworkChangedReceiver : BroadcastReceiver() {
        @RequiresApi(Build.VERSION_CODES.M)
        override fun onReceive(context: Context?, intent: Intent?) {
            val connectivityManager =
                context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork = connectivityManager.activeNetwork
            if (activeNetwork != null) {
                viewModel.checkInternetConnection()
                binding.refreshBtn.setOnClickListener {
                    viewModel.checkInternetConnection()
                }
            } else {
                viewModel.changeConnection(false)
                binding.refreshBtn.setOnClickListener {
                    //do nothing
                }
            }
        }
    }

    private fun handleNetworkConnectivity(hasConnection: Boolean) {
        if (hasConnection) {
            HomeFragment.getInstance()
            binding.noConnectionCardLayout.visibility = View.GONE
            binding.navHostFragment.visibility = View.VISIBLE

        } else {
            binding.noConnectionCardLayout.visibility = View.VISIBLE
            binding.navHostFragment.visibility = View.GONE
        }
    }
}

