package com.bekhruz.weatherforecast.utils.viewExt

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import androidx.core.graphics.drawable.toDrawable
import com.bekhruz.weatherforecast.R


object LottieLoaderDialog {
    private fun loadDialog(context:Context):Dialog{
       val dialogBuilder = Dialog(context)
        dialogBuilder.let {
            it.show()
            it.window?.setBackgroundDrawable(Color.TRANSPARENT.toDrawable())
            it.setContentView(R.layout.lottie_loader)
            it.setCancelable(false)
            it.setCanceledOnTouchOutside(false)
            return it
        }
    }
    fun hideLoading(context:Context){
        loadDialog(context).let {
            if (it.isShowing) it.cancel()
        }
    }
    fun showLoading(context: Context){
        hideLoading(context)
        loadDialog(context)
    }
}