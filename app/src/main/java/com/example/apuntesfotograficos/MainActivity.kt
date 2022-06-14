package com.example.apuntesfotograficos

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.apuntesfotograficos.db.NoteDB
import com.example.apuntesfotograficos.utils.UIUtils

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setTheme(R.style.Theme_ApuntesFotograficos)
        setContentView(R.layout.activity_main)
        uiUtils = UIUtils(this)
        dbRoom = Room.databaseBuilder(
            applicationContext,
            NoteDB::class.java, "note"
        ).build()
    }

    companion object{
        lateinit var uiUtils:UIUtils
        var dbRoom: NoteDB? = null
    }

    override fun onBackPressed() {
//        super.onBackPressed()
    }

}