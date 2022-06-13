package com.example.apuntesfotograficos

import android.app.Application
import androidx.room.Room
import com.example.apuntesfotograficos.db.NoteDB

class NoteApp:Application() {
    val room = Room.databaseBuilder(this, NoteDB::class.java,"note").build()

}