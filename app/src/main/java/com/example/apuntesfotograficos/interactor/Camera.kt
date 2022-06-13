package com.example.apuntesfotograficos.interactor

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import androidx.core.content.FileProvider
import com.example.apuntesfotograficos.interfaces.ICamera
import com.example.apuntesfotograficos.utils.CommonUtils
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class Camera: ICamera.Iterator {
    lateinit var currentPhotoPath: String
    val REQUEST_TAKE_PHOTO = 1
    var mActivty: Activity? = null

    @Throws(IOException::class)
    fun createImageFile(name: String, timeStamp:String): File {
        // Create an image file name
//        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File = mActivty?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
        return File.createTempFile(
            "${name}_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }

    override fun initCamera(activity: Activity) {
        mActivty = activity
    }

    override fun captureImage(name: String, timeStamp:String) {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            takePictureIntent.resolveActivity(mActivty!!.packageManager)?.also {
                // Create the File where the photo should go
                val photoFile: File? = try {
                    createImageFile(name, timeStamp)
                } catch (ex: IOException) {
                    // Error occurred while creating the File
                    null
                }
                // Continue only if the File was successfully created
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        mActivty!!,
                        "com.example.fileprovider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    mActivty!!.startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO)
                    resultImage()
                }
            }
        }
    }

    fun resultImage(){
        try{
            val file = File (currentPhotoPath)
            if (file.exists()){
                var data = CommonUtils.convertFileToByte(file)
                if (data!!.size == 0)
                    file.delete()
            }
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

}