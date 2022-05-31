package com.example.apuntesfotograficos.utils

import java.io.File

class CommonUtils {

    companion object{
        fun listarImagenes():MutableList<String>? {
            var directory = File(Constans.URL_IMAGES)
            var listaImagenes: MutableList<String> = listOf<String>().toMutableList()
            directory.walk().forEach {
                if (it != null) listaImagenes.add("$it")
            }
            listaImagenes.removeAt(0)
            println("TAMAÑO -----> ${listaImagenes?.size}")
            return listaImagenes
        }

        fun listar() {
//            var directory = File(Constans.URL_IMAGES)
            var listaImagenes: Array<String>? = null
            File(Constans.URL_IMAGES).walk().forEach {
                println(it)
            }
        }
    }

}