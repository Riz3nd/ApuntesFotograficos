package com.example.apuntesfotograficos.dao

import androidx.room.*
import com.example.apuntesfotograficos.model.User

@Dao
interface UserDAO {
//    @Query("SELECT * FROM User")
//    suspend fun  getAllUser():List<User>
//    @Query("SELECT * FROM User WHERE user_id = :id")
//    suspend fun getUserById(id: Int): User
    @Query("SELECT * FROM User WHERE user_email = :email AND user_password = :passwd")
    suspend fun sessionUser(email: String, passwd: String): User
    @Update
    suspend fun updateUser(User: User)
    @Insert
    suspend fun insertUser(User: User)
    @Delete
    suspend fun deleteUser(User : User)
}