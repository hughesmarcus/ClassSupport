package com.supporter.marcus.classsupport.ui.favorite

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.supporter.marcus.classsupport.data.DonorRepository
import com.supporter.marcus.classsupport.ui.EmptyListState
import com.supporter.marcus.classsupport.ui.Event
import com.supporter.marcus.classsupport.ui.State
import com.supporter.marcus.classsupport.ui.search.ProposalItem
import com.supporter.marcus.classsupport.util.mvvm.RxViewModel
import com.supporter.marcus.classsupport.util.mvvm.SingleLiveEvent
import com.supporter.marcus.classsupport.util.rx.SchedulerProvider

class FavoritesViewModell(
        private val donorRepository: DonorRepository,
        schedulerProvider: SchedulerProvider

) : RxViewModel(schedulerProvider) {

    private val state = MutableLiveData<State>()
    val states: LiveData<State>
        get() = state

    private val event = SingleLiveEvent<Event>()
    val events: LiveData<Event>
        get() = event

    fun loadProposals() {
        launch {
            event.value = LoadingProposalsEvent("fav")
            try {
                val proposals = donorRepository.getFavorites().await()
                if (proposals.isEmpty()) {
                    state.value = EmptyListState
                } else {
                    state.value = ProposalListFavState.from(proposals)
                }
                event.value = LoadingProposalsEventEnded("fav")
            } catch (error: Throwable) {
                event.value = LoadProposalsFailedEvent("fav", error)
            }
        }
    }


    data class ProposalListFavState(
            val list: MutableList<ProposalItem>
    ) : State() {
        companion object {
            fun from(list: MutableList<ProposalItem>): ProposalListFavState {
                return when {
                    list.isEmpty() -> error(" list should not be empty")
                    else -> {
                        ProposalListFavState(list)
                    }
                }
            }
        }
    }

    data class LoadingProposalsEvent(val query: String?) : Event()
    data class LoadProposalsFailedEvent(val query: String?, val error: Throwable) : Event()
    data class LoadingProposalsEventEnded(val query: String?) : Event()
}