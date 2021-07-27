package com.test.photoBooth.structure

import androidx.multidex.MultiDexApplication
import com.test.external.di.ExternalModule
import com.test.photoBooth.feature.di.ViewModelModule
import com.test.repository.di.RepoModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin

class BaseApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@BaseApplication)
            modules(
                listOf(
                    ExternalModule,
                    ViewModelModule,
                    RepoModule
                )
            )
        }
    }

    override fun onTerminate() {
        super.onTerminate()
        stopKoin()
    }
}