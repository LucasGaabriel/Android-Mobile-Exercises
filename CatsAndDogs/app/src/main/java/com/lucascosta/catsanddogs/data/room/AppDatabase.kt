package com.lucascosta.catsanddogs.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.lucascosta.catsanddogs.data.dao.CatDAO
import com.lucascosta.catsanddogs.data.dao.DogDAO
import com.lucascosta.catsanddogs.data.model.CatModel
import com.lucascosta.catsanddogs.data.model.DogModel

@Database(entities = [CatModel::class, DogModel::class], version = 1)
abstract class AppDatabase() : RoomDatabase() {

    abstract fun CatDAO(): CatDAO
    abstract fun DogDAO(): DogDAO

    companion object {
        private lateinit var INSTANCE: AppDatabase
        fun getDatabase(context: Context): AppDatabase {

            if (!::INSTANCE.isInitialized) {

                synchronized(AppDatabase::class) {

                    INSTANCE =
                        Room.databaseBuilder(context, AppDatabase::class.java, "mydatabase.db")
                            .allowMainThreadQueries()
                            .build()
                }
            }
            return INSTANCE
        }
    }
}
