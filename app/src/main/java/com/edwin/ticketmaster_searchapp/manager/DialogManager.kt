package com.edwin.ticketmaster_searchapp.manager

import android.content.Context
import androidx.appcompat.app.AlertDialog

class DialogManager {

    companion object {
        fun showErrorAlert(
            context: Context,
            title: String,
            message: String,
            positiveCallback: (() -> Unit),
            positiveButtonText: String = "Ok",
            negativeCallback: (() -> Unit)? = null,
            negativeButtonText: String? = null,
        ) {
            val builder: AlertDialog.Builder = AlertDialog.Builder(context)

            builder.setMessage(message)
            builder.setTitle(title)
            builder.setCancelable(false)
            builder.setPositiveButton(positiveButtonText) { p0, p1 ->
                p0.cancel()
                positiveCallback.invoke()
            }
            negativeButtonText?.apply {
                builder.setNegativeButton(this) { p0, p1 ->
                    p0.cancel()
                    negativeCallback?.invoke()
                }
            }

            val alertDialog = builder.create()
            alertDialog.show()
        }
    }

}