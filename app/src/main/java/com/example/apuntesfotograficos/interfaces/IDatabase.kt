package com.example.apuntesfotograficos.interfaces

import android.content.Context
import com.example.apuntesfotograficos.model.Note
import com.example.apuntesfotograficos.model.User

interface IDatabase {
    interface View{}

    interface Presenter{
        fun registerUser(user: User, context: Context?)
        fun initSession(user: User, context: Context?):User?
        fun createNote(note: Note, context: Context?)
    }

    interface Iterator{
        fun registerUser(user: User, context: Context?)
        fun initSession(user: User, context: Context?):User?
        fun createNote(note: Note, context: Context?)
    }
}