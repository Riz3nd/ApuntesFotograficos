package com.example.apuntesfotograficos.utils

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class CameraUtil(activity: Activity) {
    lateinit var currentPhotoPath: String
    val REQUEST_TAKE_PHOTO = 1
    var mActivity: Activity? = null
    var dirImg:String? = null
    init {
        mActivity = activity
    }

    @Throws(IOException::class)
    fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File = mActivity?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }

    fun captureImage() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            takePictureIntent.resolveActivity(mActivity!!.packageManager)?.also {
                // Create the File where the photo should go
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    // Error occurred while creating the File
                    null
                }
                // Continue only if the File was successfully created
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        mActivity!!,
                        "com.example.fileprovider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    mActivity!!.startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO)
                    dirImg = currentPhotoPath
                }
            }
        }
    }

    fun resultNote(){
        try{
            val file = File (currentPhotoPath)
            if (file.exists()){
                var data = convertFileToByte(file)
                if (data!!.size == 0)
                    file.delete()
            }
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    private fun convertFileToByte(file:File ): ByteArray? {
        var res = ByteArray(0)
        try {
            FileInputStream(file).use { fileOutputStream ->
                res = ByteArray(file.length().toInt())
                val count: Int = fileOutputStream.read(res)
                Log.i("fileOutput", count.toString())
            }
        } catch (e: java.lang.Exception) {
            Log.e("convertFileToByte", e.message!!)
        }
        return res
    }

//    fun listarImagenes() {
//        var directory = File(Constans.URL_IMAGES)
//        var listaImagenes: Array<File>? = null
//        directory.walk().forEach {
//            listaImagenes = arrayOf(it)
//        }
//    }

}