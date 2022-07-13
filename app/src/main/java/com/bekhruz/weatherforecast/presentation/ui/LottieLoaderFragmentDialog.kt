package com.bekhruz.weatherforecast.presentation.ui

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.airbnb.lottie.LottieDrawable
import com.bekhruz.weatherforecast.databinding.FragmentLottieLoaderDialogBinding
import com.bekhruz.weatherforecast.databinding.FragmentLottieLoaderDialogBinding.*

class LottieLoaderFragmentDialog : DialogFragment() {

    private lateinit var binding: FragmentLottieLoaderDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (dialog == null) {
            return
        }
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.lottieDotLoader.show()
        binding.lottieDotLoader.repeatCount = LottieDrawable.INFINITE
        binding.lottieDotLoader.playAnimation()

    }

    companion object {
        const val TAG = "LottieLoaderFragmentDialog"
        fun getInstance(): LottieLoaderFragmentDialog {
            return LottieLoaderFragmentDialog()
        }
    }

    private fun View.show() {
        this.visibility = (View.VISIBLE)
    }
}