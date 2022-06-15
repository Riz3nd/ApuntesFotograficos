package com.example.apuntesfotograficos.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Share (
    @PrimaryKey(autoGenerate = true)
    val share_id:Int,
    val share_user:String,
    val share_cate:String
)