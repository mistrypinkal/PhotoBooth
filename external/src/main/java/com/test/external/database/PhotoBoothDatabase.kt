package com.test.external.database

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * Database
 *
 * @author Pinkal Mistry
 * @version 1.0
 * @since 27 March 2021
 */

@Database(entities = [PhotoBooth::class], version = 1)
abstract class PhotoBoothDatabase : RoomDatabase() {

    abstract fun photoBoothDAO(): PhotoBoothDAO

}