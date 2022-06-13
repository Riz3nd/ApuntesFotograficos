package com.example.apuntesfotograficos.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Category(
    @PrimaryKey(autoGenerate = true)
    var cate_id:Int,
    var cate_name:String
)