package com.example.apuntesfotograficos.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class User(
    @PrimaryKey(autoGenerate = true)
    var user_id:Int,
    var user_name:String,
    var user_email:String,
    var user_password:String
)