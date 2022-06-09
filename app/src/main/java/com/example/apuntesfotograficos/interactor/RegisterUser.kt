package com.example.apuntesfotograficos.interactor

import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.apuntesfotograficos.api.AdminSQLiteOpenHelper
import com.example.apuntesfotograficos.interfaces.IRegister
import com.example.apuntesfotograficos.model.User

class RegisterUser:IRegister.Iterator {

    override fun registerUser(user: User, context: Context?) {
        Log.e("Register","HOLAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
        print("HOLAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
        val admin = AdminSQLiteOpenHelper(context,"usuario", null, 1)
        val bd = admin.writableDatabase
        var registro = ContentValues()
        registro.put("user_name",user.user_name)
        registro.put("user_email",user.user_email)
        registro.put("user_password",user.user_password)
        bd.insert("usuario", null, registro)
        bd.close()
        Toast.makeText(context,"Usuario Creado",Toast.LENGTH_LONG).show()
    }

}