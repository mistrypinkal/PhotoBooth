package com.test.photoBooth.feature.di

import com.test.photoBooth.feature.home.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Koin External module
 *
 * @author Pinkal Mistry
 * @version 1.0
 * @since 27 March 2021
 */
@JvmField
val ViewModelModule = module {

    viewModel { MainViewModel(get()) }
}