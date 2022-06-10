package com.example.apuntesfotograficos.interfaces

import android.content.Context
import com.example.apuntesfotograficos.model.User

interface IDatabase {
    interface View{}

    interface Presenter{
        fun registerUser(user: User, context: Context?)
        fun initSession(user: User, context: Context?):User?
    }

    interface Iterator{
        fun registerUser(user: User, context: Context?)
        fun initSession(user: User, context: Context?):User?
    }
}