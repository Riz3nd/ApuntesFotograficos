package com.example.apuntesfotograficos.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import java.io.File
import java.io.FileInputStream

class CommonUtils {

    companion object{
        fun getListImages():MutableList<String>? {
            var directory = File(Constans.URL_IMAGES)
            var listaImagenes: MutableList<String> = listOf<String>().toMutableList()
            directory.walk().forEach {
                if (it != null) listaImagenes.add("$it")
            }
            if(listaImagenes.size > 0)
                listaImagenes.removeAt(0)
            listaImagenes.reverse()
//            println("TAMAÑO -----> ${listaImagenes?.size}")
            return listaImagenes
        }

        fun getListTitles():MutableList<String>? {
            var directory = File(Constans.URL_IMAGES)
            var listaTitulos: MutableList<String> = listOf<String>().toMutableList()
            var getTitulos: MutableList<String> = listOf<String>().toMutableList()

            directory.walk().forEach {
                if (it != null){
                    listaTitulos.add("${it.toString().replace(Constans.URL_IMAGES,"")}")
                }
            }
//            if(listaTitulos.size > 0)
//                listaTitulos.removeAt(0)
            listaTitulos.reverse()

            listaTitulos.forEach {
//                getTitulos = it.toString().split("*").toMutableList()
                println("TITULOS  -  $it")
            }

            return listaTitulos
        }

        fun getBitmap( pathName: String?) : Bitmap?{
            var file : File =  File(pathName)
            var bm : Bitmap? = null
            if(file.exists()){
                bm = BitmapFactory.decodeFile(pathName)
            }
            return bm
        }

        fun convertFileToByte(file:File ): ByteArray? {
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
    }

}