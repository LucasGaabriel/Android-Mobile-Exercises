package com.lucascosta.catsanddogs.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.lucascosta.catsanddogs.data.model.CatModel

@Dao
interface CatDAO {
    @Insert
    fun insert(p: CatModel): Long

    @Update
    fun update(p: CatModel): Int

    @Delete
    fun delete(p: CatModel)

    @Query("SELECT * FROM Dog WHERE id = :id")
    fun getById(id: Int): CatModel
}