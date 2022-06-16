package com.example.apuntesfotograficos.dao

import androidx.room.*
import com.example.apuntesfotograficos.model.Note

@Dao
interface NoteDAO {
    @Query("SELECT * FROM Note WHERE note_user = :id")
    suspend fun  getAllNotes(id: Int):List<Note>
    @Query("SELECT * FROM Note WHERE note_category = :category AND note_user = :id")
    suspend fun getNoteByCategory(category: String, id: Int):List<Note>
    @Query("SELECT * FROM Note WHERE note_category = :category")
    suspend fun getNoteByCategoryName(category: String):List<Note>
    @Query("SELECT * FROM Note WHERE note_like = :like AND note_user = :id")
    suspend fun getAllNotesLike(like: Boolean, id: Int):List<Note>
    @Query("UPDATE Note SET note_name = :new_name WHERE note_name = :old_name")
    suspend fun updateNote(new_name: String, old_name: String)
    @Query("UPDATE Note SET note_like = :like WHERE note_name = :name")
    suspend fun updateNoteLike(like: Boolean, name: String)
    @Query("UPDATE Note SET note_category = :new_name_cat WHERE note_category = :name_cat")
    suspend fun updateNoteCategory(new_name_cat: String, name_cat: String)
    @Insert
    suspend fun insertNote(note: Note)
    @Query("DELETE FROM Note WHERE note_category = :cate_name")
    suspend fun deleteNotes(cate_name :String)
    @Query("DELETE FROM Note WHERE note_name = :note_name")
    suspend fun deleteNote(note_name :String)
}