package com.example.apuntesfotograficos.utils

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.*
import com.example.apuntesfotograficos.R
import com.example.apuntesfotograficos.interfaces.onItemClickListener
import com.example.apuntesfotograficos.model.Category
import com.example.apuntesfotograficos.presenter.CameraPesenter

class UIUtils(activity: Activity) {
    var mActivity: Activity
    lateinit var mListener: onItemClickListener.onClickDialog

    init {
        mActivity = activity

    }

    fun createDialog(cat_title: String):Dialog {
        val dialog = Dialog(mActivity)
        dialog.setContentView(R.layout.dialog)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val title = dialog.findViewById<TextView>(R.id.title_dialog)
        title.text = "$cat_title"
        dialog.create()
        dialog.show()
        return dialog
    }

    fun createDialogCateShare(/*listCategory: List<Category>?*/):Dialog {
        val dialog = Dialog(mActivity)
        dialog.setContentView(R.layout.dialog_share_note)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        val spinner_category = dialog.findViewById<Spinner>(R.id.spinner_category)
//        var mutableList = mutableListOf<String>()
//        listCategory?.forEach{
//            mutableList.add(it.cate_name)
//        }
//        var array = mutableList.toTypedArray()
//        val adaterSpinner = ArrayAdapter<String>(mActivity, android.R.layout.simple_spinner_item, array)
//        spinner_category.adapter = adaterSpinner
        dialog.create()
        dialog.show()
        return dialog
    }

    fun createDialogEditCate():Dialog {
        val dialog = Dialog(mActivity)
        dialog.setContentView(R.layout.dialog_share_note)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.create()
        dialog.show()
        return dialog
    }

    fun createDialogNote(listCategory: List<Category>?):Dialog {
        val dialog = Dialog(mActivity)
        dialog.setContentView(R.layout.dialog_create_note)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val spinner_category = dialog.findViewById<Spinner>(R.id.spinner_category)
        var mutableList = mutableListOf<String>()
        listCategory?.forEach{
            mutableList.add(it.cate_name)
        }
        var array = mutableList.toTypedArray()
        val adaterSpinner = ArrayAdapter<String>(mActivity, android.R.layout.simple_spinner_item, array)
        spinner_category.adapter = adaterSpinner
        dialog.create()
        dialog.show()
        return dialog
    }

    fun createDialogCategory():Dialog {
        val dialog = Dialog(mActivity)
        dialog.setContentView(R.layout.dialog_create_category)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.create()
        dialog.show()
        return dialog
    }

    fun profileDialog(listener: onItemClickListener.onClickDialog):Dialog {
        val dialog = Dialog(mActivity)
        dialog.setContentView(R.layout.profile_dialog)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val btnSignOut = dialog.findViewById<Button>(R.id.btn_sign_out)
        val btnClose = dialog.findViewById<ImageView>(R.id.btn_close)
        val userName = dialog.findViewById<TextView>(R.id.user_name)
        val userEmail = dialog.findViewById<TextView>(R.id.tv_user_email)
        dialog.create()
        dialog.show()
        val preferencias  = mActivity.
        getSharedPreferences("userData", Context.MODE_PRIVATE)
        userName.text = "${preferencias.getString("name", "")}"
        userEmail.text = "${preferencias.getString("email", "")}"
        btnClose.setOnClickListener { dialog.dismiss() }
        btnSignOut.setOnClickListener {
            dialog.dismiss()
            listener.onClickDialog()
        }
        return dialog
    }

}