package com.supporter.marcus.classsupport.ui.donations

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.supporter.marcus.classsupport.data.DonorRepository
import com.supporter.marcus.classsupport.data.local.models.Donation
import com.supporter.marcus.classsupport.ui.EmptyListState
import com.supporter.marcus.classsupport.ui.Event
import com.supporter.marcus.classsupport.ui.State
import com.supporter.marcus.classsupport.util.mvvm.RxViewModel
import com.supporter.marcus.classsupport.util.mvvm.SingleLiveEvent
import com.supporter.marcus.classsupport.util.rx.SchedulerProvider

class DonationListViewModel(
        private val donorRepository: DonorRepository,
        schedulerProvider: SchedulerProvider

) : RxViewModel(schedulerProvider) {

    private val state = MutableLiveData<State>()
    val states: LiveData<State>
        get() = state

    private val event = SingleLiveEvent<Event>()
    val events: LiveData<Event>
        get() = event

    fun loadDonations() {
        launch {
            event.value = DonationListViewModel.LoadingDonationsEvent("donation")
            try {
                val donations = donorRepository.getDonations().await()
                if (donations.isEmpty()) {
                    state.value = EmptyListState
                } else {
                    state.value = DonationListViewModel.DonationListState.from(donations)
                }
                event.value = DonationListViewModel.LoadingDonationsEventEnded("donation")
            } catch (error: Throwable) {
                event.value = DonationListViewModel.LoadDonationsFailedEvent("donation", error)
            }
        }
    }

    data class DonationListState(
            val list: List<Donation>
    ) : State() {
        companion object {
            fun from(list: List<Donation>): DonationListState {
                return when {
                    list.isEmpty() -> error(" list should not be empty")
                    else -> {
                        DonationListState(list)
                    }
                }
            }
        }
    }

    data class LoadingDonationsEvent(val query: String?) : Event()
    data class LoadDonationsFailedEvent(val query: String?, val error: Throwable) : Event()
    data class LoadingDonationsEventEnded(val query: String?) : Event()
}