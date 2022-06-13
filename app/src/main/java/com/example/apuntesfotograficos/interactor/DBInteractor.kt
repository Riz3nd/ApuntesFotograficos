package com.example.apuntesfotograficos.interactor

import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.apuntesfotograficos.db.AdminSQLiteOpenHelper
import com.example.apuntesfotograficos.interfaces.IDatabase
import com.example.apuntesfotograficos.model.Note
import com.example.apuntesfotograficos.model.User

class DBInteractor:IDatabase.Iterator {

    override fun registerUser(user: User, context: Context?) {
        val admin = AdminSQLiteOpenHelper(context,"usuario", null, 1)
        val bd = admin.writableDatabase
        var registro = ContentValues()
        registro.put("user_name",user.user_name)
        registro.put("user_email",user.user_email)
        registro.put("user_password",user.user_password)
        bd.insert("usuario", null, registro)
        bd.close()
        Toast.makeText(context,"Registro Exitoso!",Toast.LENGTH_LONG).show()
    }

//    override fun initSession(user: User, context: Context?):User? {
//        val admin = AdminSQLiteOpenHelper(context, "usuario", null, 1)
//        val bd = admin.writableDatabase
//        var userData = User()
//        val fila = bd.rawQuery("select user_id,user_name,user_email from usuario where user_email='${user.user_email.toString()}' " +
//                "and user_password='${user.user_password.toString()}'", null)
//        if (fila.moveToFirst()) {
//            userData.user_id = fila.getInt(0)
//            userData.user_name = fila.getString(1)
//            userData.user_email = fila.getString(2)
//        } else{
//            bd.close()
//            Toast.makeText(context, "Correo o contraseña incorrectos",  Toast.LENGTH_LONG).show()
//            return null
//        }
//        Log.e("InitSession","${userData?.user_name} - ${userData?.user_email}")
//        bd.close()
//        return userData
//    }

    override fun createNote(note: Note, context: Context?) {
        val admin = AdminSQLiteOpenHelper(context,"note", null, 1)
        val bd = admin.writableDatabase
        var registro = ContentValues()
        registro.put("user_name",note.note_name)
        bd.insert("usuario", null, registro)
        bd.close()
        Toast.makeText(context,"Registro Exitoso!",Toast.LENGTH_LONG).show()
    }

}