package com.example.apuntesfotograficos.dao

import androidx.room.*
import com.example.apuntesfotograficos.model.Category
@Dao
interface CategoryDAO {
    @Query("SELECT * FROM Category")
    suspend fun  getAllCategory():List<Category>
    @Query("SELECT * FROM Category WHERE cate_id = :id")
    suspend fun getCategoryById(id: Int): Category
    @Update
    suspend fun updateCategory(Category: Category)
    @Insert
    suspend fun insertCategory(Category: Category)
    @Delete
    suspend fun deleteCategory(Category : Category)
}