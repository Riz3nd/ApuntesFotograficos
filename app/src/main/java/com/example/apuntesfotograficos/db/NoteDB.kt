package com.example.apuntesfotograficos.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.apuntesfotograficos.dao.CategoryDAO
import com.example.apuntesfotograficos.dao.NoteDAO
import com.example.apuntesfotograficos.dao.UserDAO
import com.example.apuntesfotograficos.model.Category
import com.example.apuntesfotograficos.model.Note
import com.example.apuntesfotograficos.model.User

@Database(
    entities = [Note::class, Category::class, User::class],
    version = 1
)
abstract class NoteDB: RoomDatabase() {
    abstract fun noteDao():NoteDAO
    abstract fun categoryDao(): CategoryDAO
    abstract fun userDao(): UserDAO
}