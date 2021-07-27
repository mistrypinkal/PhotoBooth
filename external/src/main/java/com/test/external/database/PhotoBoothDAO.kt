package com.test.external.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

/**
 * DAO
 *
 * @author Pinkal Mistry
 * @version 1.0
 * @since 27 March 2021
 */
@Dao
interface PhotoBoothDAO {

    @Insert
    fun insertPhotoBoothData(photoBooth: PhotoBooth)

    @Query("SELECT * FROM photo_booth_table")
    fun getAllPhotoBoothData(): List<PhotoBooth>

}
