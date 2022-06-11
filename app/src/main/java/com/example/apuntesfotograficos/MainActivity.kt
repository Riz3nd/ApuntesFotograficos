package com.example.apuntesfotograficos

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.apuntesfotograficos.utils.UIUtils

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_ApuntesFotograficos)
        setContentView(R.layout.activity_main)
        uiUtils = UIUtils(this)
    }

    companion object{
        lateinit var uiUtils:UIUtils
    }

    override fun onBackPressed() {
//        super.onBackPressed()
    }

}