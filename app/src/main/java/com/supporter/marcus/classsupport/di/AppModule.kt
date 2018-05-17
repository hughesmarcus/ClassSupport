package com.supporter.marcus.classsupport.di

import com.supporter.marcus.classsupport.data.DonorRepository
import com.supporter.marcus.classsupport.data.DonorRepositoryImpl
import com.supporter.marcus.classsupport.ui.search.SearchViewModel
import com.supporter.marcus.classsupport.util.rx.ApplicationSchedulerProvider
import com.supporter.marcus.classsupport.util.rx.SchedulerProvider
import org.koin.android.architecture.ext.viewModel
import org.koin.dsl.module.applicationContext

/**
 * App Components
 */
val donorAppModule = applicationContext {

    viewModel { SearchViewModel(get(), get()) }



    // Weather Data Repository
    bean { DonorRepositoryImpl(get()) as DonorRepository }

    // Rx Schedulers
    bean { ApplicationSchedulerProvider() as SchedulerProvider }


}

// Gather all app modules
val onlineWeatherApp = listOf(donorAppModule, remoteDatasourceModule)
