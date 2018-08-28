package com.supporter.marcus.classsupport.di

import android.arch.persistence.room.Room
import com.supporter.marcus.classsupport.data.DonorRepository
import com.supporter.marcus.classsupport.data.DonorRepositoryImpl
import com.supporter.marcus.classsupport.data.local.ProposalDatabase
import com.supporter.marcus.classsupport.ui.detail.ProposalDetailViewModel
import com.supporter.marcus.classsupport.ui.donations.AddDonationViewModel
import com.supporter.marcus.classsupport.ui.donations.DonationListViewModel
import com.supporter.marcus.classsupport.ui.favorite.FavoritesViewModel
import com.supporter.marcus.classsupport.ui.home.HomeViewModel
import com.supporter.marcus.classsupport.ui.search.SearchFilterViewModel
import com.supporter.marcus.classsupport.ui.search.SearchViewModel
import com.supporter.marcus.classsupport.util.rx.ApplicationSchedulerProvider
import com.supporter.marcus.classsupport.util.rx.SchedulerProvider
import org.koin.android.architecture.ext.viewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module.applicationContext

/**
 * App Components
 */
val donorAppModule = applicationContext {

    viewModel { SearchViewModel(get(), get()) }
    viewModel { ProposalDetailViewModel(get(), get()) }
    viewModel { SearchFilterViewModel() }
    viewModel { HomeViewModel(get(), get()) }
    viewModel { FavoritesViewModel(get(), get()) }
    viewModel { DonationListViewModel(get(), get()) }
    viewModel { AddDonationViewModel(get(), get()) }


    bean { DonorRepositoryImpl(get(), get(), get()) as DonorRepository }

    // Rx Schedulers
    bean { ApplicationSchedulerProvider() as SchedulerProvider }
    //room database
    bean {
        Room.databaseBuilder(androidApplication(), ProposalDatabase::class.java, "proposal-db")
                .build()
    }
    //expose DAO
    bean { get<ProposalDatabase>().favoriteDAO() }
    bean { get<ProposalDatabase>().proposalDAO() }
    bean { get<ProposalDatabase>().donationDAO() }

}

// Gather all app modules
val onlineDonorApp = listOf(donorAppModule, remoteDatasourceModule)
