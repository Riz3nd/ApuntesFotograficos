package com.example.apuntesfotograficos.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Note(
    @PrimaryKey(autoGenerate = true)
    val note_id:Int,
    val note_name:String,
    val note_category:String,
    val note_date: String,
    val note_content:String,
    val note_status:Boolean,
    val note_src: String,
    val note_share:Int
)



