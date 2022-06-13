package com.example.apuntesfotograficos.presenter

import com.example.apuntesfotograficos.interfaces.ICamera

class CameraPesenter(private val view:ICamera.View, private val iterator: ICamera.Iterator): ICamera.Presenter {
    override fun getImage(name: String, timeStamp:String) {
        iterator.initCamera(view.getCamera())
        iterator.captureImage(name, timeStamp)
    }

}