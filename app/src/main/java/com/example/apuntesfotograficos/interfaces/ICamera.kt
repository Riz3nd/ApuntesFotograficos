package com.example.apuntesfotograficos.interfaces

import android.app.Activity

interface ICamera {

    interface View{
        fun getCamera():Activity
    }

    interface Presenter{
        fun getImage(name: String, timeStamp:String)
    }

    interface Iterator{
        fun initCamera(activity: Activity)
        fun captureImage(name: String, timeStamp:String)
    }

}