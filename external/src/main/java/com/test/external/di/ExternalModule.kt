package com.test.external.di

import androidx.room.Room
import com.test.external.database.PhotoBoothDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

/**
 * Koin External module
 *
 * @author Pinkal Mistry
 * @version 1.0
 * @since 27 March 2021
 */
@JvmField
val ExternalModule = module {

    // Room Database
    single {
        Room.databaseBuilder(
            androidApplication(),
            PhotoBoothDatabase::class.java,
            "Photo_booth"
        ).build()
    }

    // BirdsDAO
    single { get<PhotoBoothDatabase>().photoBoothDAO() }

}