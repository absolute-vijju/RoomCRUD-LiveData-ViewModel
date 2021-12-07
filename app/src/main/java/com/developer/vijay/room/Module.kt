package com.developer.vijay.room

import android.content.Context
import androidx.room.Room
import com.developer.vijay.room.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        AppDatabase::class.java,
        "db_users"
    ).build()

    @Singleton
    @Provides
    fun provideDAO(db: AppDatabase) = db.getDao()

}