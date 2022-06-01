package com.example.apuntesfotograficos.utils

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.Button
import com.example.apuntesfotograficos.R

class FragmentUtils {
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


        fun onCreateDialog(activity: Activity): Dialog {
            return activity!!.let {
                val builder = AlertDialog.Builder(it)
                // Get the layout inflater
                val inflater = activity.layoutInflater;
                val btnDialogCancel = activity.findViewById<Button>(R.id.btn_dialog_cancel)

                // Inflate and set the layout for the dialog
                // Pass null as the parent view because its going in the dialog layout
                builder.setView(inflater.inflate(R.layout.dialog_signin, null))
                    // Add action buttons
                    .setPositiveButton("Entrar",
                        DialogInterface.OnClickListener { dialog, id ->
                            // sign in the user ...
                        })
                    .setNegativeButton(R.string.cancel,
                        DialogInterface.OnClickListener { dialog, id ->
//                            getDialog().cancel()
                        })
                builder.create()
            } ?: throw IllegalStateException("Activity cannot be null")
        }

    }
}