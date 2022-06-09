package com.example.apuntesfotograficos.utils

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.Button
import com.example.apuntesfotograficos.R
import com.example.apuntesfotograficos.interfaces.onItemClickListener
import com.example.apuntesfotograficos.presenter.CameraPesenter

class UIUtils {
    companion object{
        var returns = false

        fun createDialog(activity: Activity) {
            val dialog = Dialog(activity)
            dialog.setContentView(R.layout.dialog)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val btnDialogCancel = dialog.findViewById<Button>(R.id.btn_dialog_cancel)
            dialog.create()
            dialog.show()
            btnDialogCancel.setOnClickListener { dialog.dismiss() }
        }

        fun createDialogNote(activity: Activity, presenter: CameraPesenter) {
            val dialog = Dialog(activity)
            dialog.setContentView(R.layout.dialog_create_note)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val btnDialogCancel = dialog.findViewById<Button>(R.id.btn_dialog_ok)
            dialog.create()
            dialog.show()
            btnDialogCancel.setOnClickListener {
                presenter.getImage()
                dialog.dismiss()
            }
        }

    }

}