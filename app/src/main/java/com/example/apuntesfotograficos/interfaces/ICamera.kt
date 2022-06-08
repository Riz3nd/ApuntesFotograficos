package com.example.apuntesfotograficos.interfaces

import android.app.Activity

interface ICamera {

    interface View{
        fun getCamera():Activity
    }

    interface Presenter{
        fun getImage()
    }

    interface Iterator{
        fun initCamera(activity: Activity)
        fun captureImage()
    }

}