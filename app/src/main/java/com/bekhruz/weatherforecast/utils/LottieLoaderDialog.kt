package com.bekhruz.weatherforecast.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import androidx.core.graphics.drawable.toDrawable
import com.bekhruz.weatherforecast.R


object LottieLoaderDialog {
    var dialogBuilder:Dialog? = null
     fun loadDialog(context:Context, shouldLoad:Boolean){
       dialogBuilder = Dialog(context)
        dialogBuilder?.let {
            it.show()
            it.window?.setBackgroundDrawable(Color.TRANSPARENT.toDrawable())
            it.setContentView(R.layout.fragment_lottie_loader_dialog)
            it.setCancelable(false)
            it.setCanceledOnTouchOutside(false)
        }
        if (shouldLoad) dialogBuilder
        else dialogBuilder?.dismiss()
    }

}