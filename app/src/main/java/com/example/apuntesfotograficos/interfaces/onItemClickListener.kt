package com.example.apuntesfotograficos.interfaces

import android.app.Dialog

interface onItemClickListener {
    fun onItemClick(position: Int)
    fun onItemLongClick(position: Int)

    interface onClickDialog{
        fun onClickDialog()
    }
}