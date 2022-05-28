package com.example.apuntesfotograficos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.apuntesfotograficos.utils.CameraUtil

class MainActivity : AppCompatActivity() {
    var camara: CameraUtil? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        camara = CameraUtil(this)
    }

    override public fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        dir = camara?.uriNote(resultCode)
        super.onActivityResult(requestCode, resultCode, data)

    }
}