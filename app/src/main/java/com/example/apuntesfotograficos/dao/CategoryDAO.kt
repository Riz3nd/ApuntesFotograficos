package com.example.apuntesfotograficos.dao

import androidx.room.*
import com.example.apuntesfotograficos.model.Category
@Dao
interface CategoryDAO {
    @Query("SELECT * FROM Category WHERE cate_user = :id")
    suspend fun  getAllCategory(id: Int):List<Category>
    @Query("SELECT cate_name FROM Category WHERE cate_user = :id")
    suspend fun  getAllCategoryName(id: Int):Array<String>
    @Update
    suspend fun updateCategory(Category: Category)
    @Insert
    suspend fun insertCategory(Category: Category)
    @Delete
    suspend fun deleteCategory(Category : Category)
}