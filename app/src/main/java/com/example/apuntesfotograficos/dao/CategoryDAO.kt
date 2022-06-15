package com.example.apuntesfotograficos.dao

import androidx.room.*
import com.example.apuntesfotograficos.model.Category
@Dao
interface CategoryDAO {
    @Query("SELECT * FROM Category WHERE cate_user = :id")
    suspend fun  getAllCategory(id: Int):List<Category>
    @Query("SELECT cate_name FROM Category WHERE cate_user = :id")
    suspend fun  getAllCategoryName(id: Int):Array<String>
    @Query("UPDATE Category SET cate_name = :new_cate_name WHERE cate_name = :cate_name")
    suspend fun updateCategory(cate_name : String, new_cate_name : String)
    @Insert
    suspend fun insertCategory(Category: Category)
    @Query("DELETE FROM Category WHERE cate_name = :cate_name")
    suspend fun deleteCategory(cate_name : String)
}