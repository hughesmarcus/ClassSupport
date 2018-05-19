package com.supporter.marcus.classsupport.ui.home

import android.arch.lifecycle.ViewModel
import com.supporter.marcus.classsupport.data.DonorRepository
import com.supporter.marcus.classsupport.util.mvvm.RxViewModel
import com.supporter.marcus.classsupport.util.rx.SchedulerProvider

class HomeViewModel(private val donorRepository: DonorRepository,
                    schedulerProvider: SchedulerProvider
) : RxViewModel(schedulerProvider)
