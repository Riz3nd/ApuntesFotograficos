package com.example.apuntesfotograficos.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.apuntesfotograficos.model.Share

@Dao
interface ShareDAO {
    @Query("SELECT share_cate FROM Share WHERE share_user = :user_cate")
    suspend fun  getShareAllCategory(user_cate: String):List<String>
//    @Query("SELECT * FROM Share WHERE share_user = :user_cate AND share_cate = :share_cate")
//    suspend fun  getAllCategoryShare(user_cate: String, share_cate: String):Array<String>
    @Insert
    suspend fun  insertShare(share: Share)

}