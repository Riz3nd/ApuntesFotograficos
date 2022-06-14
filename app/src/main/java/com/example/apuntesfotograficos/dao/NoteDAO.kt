package com.example.apuntesfotograficos.dao

import androidx.room.*
import com.example.apuntesfotograficos.model.Note

@Dao
interface NoteDAO {
    @Query("SELECT * FROM Note")
    suspend fun  getAllNotes():List<Note>
    @Query("SELECT * FROM Note WHERE note_category = :category")
    suspend fun getNoteByCategory(category: String):List<Note>
    @Query("SELECT * FROM Note WHERE note_like = :like")
    suspend fun getAllNotesLike(like: Boolean):List<Note>
    @Update
    suspend fun updateNote(note: Note)
    @Query("UPDATE Note SET note_like = :like WHERE note_name = :name")
    suspend fun updateNoteLike(like: Boolean, name: String)
    @Insert
    suspend fun insertNote(note: Note)
    @Delete
    suspend fun deleteNote(note :Note)
}