package com.supporter.marcus.classsupport.ui.donations

import android.arch.lifecycle.LiveData
import com.supporter.marcus.classsupport.data.DonorRepository
import com.supporter.marcus.classsupport.data.local.models.Donation
import com.supporter.marcus.classsupport.ui.Event
import com.supporter.marcus.classsupport.util.mvvm.RxViewModel
import com.supporter.marcus.classsupport.util.mvvm.SingleLiveEvent
import com.supporter.marcus.classsupport.util.rx.SchedulerProvider
import java.util.*

class AddDonationViewModel(
        private val donorRepository: DonorRepository,
        schedulerProvider: SchedulerProvider

) : RxViewModel(schedulerProvider) {
    private val event = SingleLiveEvent<Event>()
    val events: LiveData<Event>
        get() = event

    fun addDonation(amount: Float, date: Date, name: String) {
        donorRepository.addDonation(Donation(0, amount, date, name))
    }


}