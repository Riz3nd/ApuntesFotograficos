package com.example.apuntesfotograficos.interactor

import java.util.*

class Note {
    var noteId:Int? = null
    var noteCategory:Int? = null
    var noteDate: Date? = null
    var noteName:String? = null
    var noteContent:String? = null
    var noteStatus:Boolean = false
    var noteSrc: Base64? = null
    var noteShare:Int? = null
}

class NoteCategory{
    var categoryId:Int? = null
    var categoryName:String? = null
}