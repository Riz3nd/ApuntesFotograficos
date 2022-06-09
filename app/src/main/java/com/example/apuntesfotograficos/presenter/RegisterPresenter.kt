package com.example.apuntesfotograficos.presenter

import android.content.Context
import com.example.apuntesfotograficos.interfaces.IRegister
import com.example.apuntesfotograficos.model.User

class RegisterPresenter(private val iterator:IRegister.Iterator):IRegister.Presenter {

    override fun registerUser(user: User, context: Context?) {
        iterator.registerUser(user, context)
    }

}