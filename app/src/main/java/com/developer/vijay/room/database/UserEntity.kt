package com.developer.vijay.room.database

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_users")
data class UserEntity(
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "number")
    var number: String,
    @ColumnInfo(name = "isFavourite")
    var isFavourite: Boolean = false,
    @ColumnInfo(name = "image")
    var image: Bitmap
) {
    @PrimaryKey(autoGenerate = true)
    var userId: Long? = null
}