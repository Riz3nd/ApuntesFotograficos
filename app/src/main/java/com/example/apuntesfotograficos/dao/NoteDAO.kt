package com.example.apuntesfotograficos.dao

import androidx.room.*
import com.example.apuntesfotograficos.model.Note

@Dao
interface NoteDAO {
    @Query("SELECT * FROM Note")
    suspend fun  getAllNotes():List<Note>
    @Query("SELECT * FROM Note WHERE note_id = :id")
    suspend fun getNoteById(id: Int):Note
    @Update
    suspend fun updateNote(note: Note)
    @Insert
    suspend fun insertNote(note: Note)
    @Delete
    suspend fun deleteNote(note :Note)
}