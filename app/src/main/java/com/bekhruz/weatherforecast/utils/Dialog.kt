package com.bekhruz.weatherforecast.utils

import android.content.Context
import com.google.android.material.dialog.MaterialAlertDialogBuilder



fun showDialog(
    context: Context,
    title:String,
    message:String,
    negativeBtnText:String,
    positiveBtnText:String,
    negativeBtnAction: Unit,
    positiveBtnAction: Unit,
) {
    MaterialAlertDialogBuilder(context)
        .setTitle(title)
        .setMessage(message)
        .setNegativeButton(negativeBtnText) { dialog, which ->
            negativeBtnAction
        }
        .setPositiveButton(positiveBtnText) { dialog, which ->
            positiveBtnAction
        }
        .show()
}
