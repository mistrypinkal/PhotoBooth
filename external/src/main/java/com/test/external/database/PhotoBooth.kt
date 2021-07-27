package com.test.external.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "photo_booth_table")
data class PhotoBooth(
    @ColumnInfo(name = "photoName") val photoName: String,
    @ColumnInfo(name = "photoLocalURL") val photoLocalURL: String?,
    @ColumnInfo(name = "createdAt") val createdAt: Long,
    @PrimaryKey @ColumnInfo(name = "id") val id: Long
)
