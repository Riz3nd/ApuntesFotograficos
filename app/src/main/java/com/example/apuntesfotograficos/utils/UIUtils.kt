package com.example.apuntesfotograficos.utils

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.Button
import com.example.apuntesfotograficos.R

class UIUtils {
    companion object{

        fun createDialog(activity: Activity) {
            val dialog = Dialog(activity)
            dialog.setContentView(R.layout.dialog_signin)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val btnDialogCancel = dialog.findViewById<Button>(R.id.btn_dialog_cancel)
            dialog.create()
            dialog.show()
            btnDialogCancel.setOnClickListener { dialog.dismiss() }
        }

    }
}