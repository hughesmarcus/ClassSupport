package com.supporter.marcus.classsupport.ui.search

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.supporter.marcus.classsupport.data.DonorRepository
import com.supporter.marcus.classsupport.data.remote.json.Proposal
import com.supporter.marcus.classsupport.ui.Event
import com.supporter.marcus.classsupport.ui.State
import com.supporter.marcus.classsupport.util.mvvm.RxViewModel
import com.supporter.marcus.classsupport.util.mvvm.SingleLiveEvent
import com.supporter.marcus.classsupport.util.rx.SchedulerProvider



class SearchViewModel(
        private val donorRepository: DonorRepository,
        schedulerProvider: SchedulerProvider
) : RxViewModel(schedulerProvider) {
    private val mStates = MutableLiveData<State>()
    val states: LiveData<State>
        get() = mStates

    private val mEvents = SingleLiveEvent<Event>()
    val events: LiveData<Event>
        get() = mEvents


    fun loadNewLocation(query: String) {
        launch {
            mEvents.value = LoadingProposalsEvent(query)
            try {
                val proposals = donorRepository.getProposal(query).await()
                mStates.value = ProposalListState.from(proposals)
            } catch (error: Throwable) {
                mEvents.value = LoadProposalsFailedEvent(query, error)
            }
        }
    }


    data class ProposalListState(
            val id: String,
            val first: Proposal,
            val lasts: List<Proposal>
    ) : State() {
        companion object {
            fun from(list: List<Proposal>): ProposalListState {
                return if (list.isEmpty()) error(" list should not be empty")
                else {
                    val first = list.first()
                    val id = first.id
                    ProposalListState(id!!, first, list.takeLast(list.size - 1))
                }
            }
        }
    }

    data class LoadingProposalsEvent(val query: String) : Event()
    data class LoadProposalsFailedEvent(val query: String, val error: Throwable) : Event()
}