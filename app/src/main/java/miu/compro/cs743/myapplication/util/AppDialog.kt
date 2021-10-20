package miu.compro.cs743.myapplication.util

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.KeyEvent
import miu.compro.cs743.myapplication.R

object AppDialog {
    var alertDialog : AlertDialog? = null

    private fun initDialog(context: Context?) {
        if (context != null) {
            if (alertDialog != null) {
                if (alertDialog!!.isShowing) {
                    return
                }
                alertDialog = null
            }
        }
    }

    fun showDialog(context: Context, message: String, positive: (() -> Unit)? = null) {
        initDialog(context)

        val alertDialogBuilder = AlertDialog.Builder(context)

        alertDialogBuilder.setMessage(message)

        alertDialogBuilder.setPositiveButton(context.getString(R.string.notification_ok)    , DialogInterface.OnClickListener { dialog, id ->
            dialog.dismiss()
            positive?.invoke()
        })
        alertDialogBuilder.setNegativeButton(context.getString(R.string.notification_cancel), DialogInterface.OnClickListener { dialog, id ->
            dialog.dismiss()
        })
        alertDialogBuilder.setOnCancelListener{dialog ->
            dialog.dismiss()
        }
        alertDialogBuilder.setOnKeyListener { dialog, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                dialog.dismiss()
                //positive?.invoke()
            }
            true
        }
        alertDialog = alertDialogBuilder.create()

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            alertDialog?.getButton(AlertDialog.BUTTON_POSITIVE)?.setTextColor(context.getColor(R.color.white))
//            alertDialog?.getButton(AlertDialog.BUTTON_POSITIVE)?.background = context.resources.getDrawable(R.color.tealish)
//        }
        alertDialog?.show()
    }
}