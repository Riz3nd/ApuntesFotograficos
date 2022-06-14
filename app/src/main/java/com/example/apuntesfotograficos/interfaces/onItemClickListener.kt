package com.example.apuntesfotograficos.interfaces

import android.app.Dialog

interface onItemClickListener {
    fun onItemClick(position: Int, id: Int)
    fun onItemLongClick(position: Int, id: Int)

    interface onClickDialog{
        fun onClickDialog()
    }
}