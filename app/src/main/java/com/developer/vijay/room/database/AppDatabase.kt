package com.developer.vijay.room.database

import android.content.Context
import androidx.room.*

@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getDao(): UserDao

    /*companion object {
        private var instance: AppDatabase? = null
        fun getDatabase(context: Context): AppDatabase {
            if (instance == null) {
                instance =
                    Room.databaseBuilder(context, AppDatabase::class.java, "user_db")
                        .build()
            }
            return instance as AppDatabase
        }
    }*/
}