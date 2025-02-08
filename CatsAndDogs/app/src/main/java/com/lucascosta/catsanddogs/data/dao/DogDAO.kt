package com.lucascosta.catsanddogs.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.lucascosta.catsanddogs.data.model.DogModel

@Dao
interface DogDAO {
    @Insert
    fun insert(p: DogModel): Long

    @Update
    fun update(p: DogModel): Int

    @Delete
    fun delete(p: DogModel)

    @Query("SELECT * FROM Dog WHERE id = :id")
    fun getById(id: Int): DogModel
}