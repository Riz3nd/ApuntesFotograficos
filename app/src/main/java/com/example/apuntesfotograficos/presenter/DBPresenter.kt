package com.example.apuntesfotograficos.presenter

import android.content.Context
import com.example.apuntesfotograficos.interfaces.IDatabase
import com.example.apuntesfotograficos.model.User

class DBPresenter(private val iterator:IDatabase.Iterator):IDatabase.Presenter {

    override fun registerUser(user: User, context: Context?) {
        iterator.registerUser(user, context)
    }

    override fun initSession(user: User, context: Context?):User? {
        return iterator.initSession(user, context)
    }

}