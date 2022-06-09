package com.example.apuntesfotograficos.interfaces

import android.app.Activity
import android.content.Context
import com.example.apuntesfotograficos.model.User

interface IRegister {
    interface View{}

    interface Presenter{
        fun registerUser(user: User, context: Context?)
    }

    interface Iterator{
        fun registerUser(user: User, context: Context?)
    }
}