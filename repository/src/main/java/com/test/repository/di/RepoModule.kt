package com.test.repository.di

import com.test.repository.PhotoBoothRepo
import org.koin.dsl.module

@JvmField
val RepoModule = module {
    single { PhotoBoothRepo(get()) }
}