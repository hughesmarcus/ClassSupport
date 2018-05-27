package com.supporter.marcus.classsupport.ui.search

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.supporter.marcus.classsupport.data.DonorRepository
import com.supporter.marcus.classsupport.data.remote.models.Proposal
import com.supporter.marcus.classsupport.ui.*
import com.supporter.marcus.classsupport.util.mvvm.RxViewModel
import com.supporter.marcus.classsupport.util.mvvm.SingleLiveEvent
import com.supporter.marcus.classsupport.util.rx.SchedulerProvider


class SearchViewModel(
        private val donorRepository: DonorRepository,
        schedulerProvider: SchedulerProvider

) : RxViewModel(schedulerProvider) {
    private var lastSearched: String? = null
    private var lastIndex: String? = "0"
    private var lastGradeType: String? = null
    private var lastSchoolType: String? = null
    private var lastState: String? = null
    private var lastSortBy: String? = null
    private var lastMax: String? = "50"
    private var total: Int? = null

    private val state = MutableLiveData<State>()
    val states: LiveData<State>
        get() = state

    private val event = SingleLiveEvent<Event>()
    val events: LiveData<Event>
        get() = event
    private var proposals: MutableLiveData<MutableList<Proposal>>? = null
    var totalProposals: MutableLiveData<Int>? = null
    var currentIndex: MutableLiveData<Int>? = null

    fun getProposals(query: String?, gradeType: String?,
                     schoolType: String?, stateUs: String?,
                     sortBy: String?, index: String?, max: String?) {
        if (proposals?.value == null || lastSearched != query) {
            proposals = MutableLiveData<MutableList<Proposal>>()
            loadNewProposals(query, gradeType,
                    schoolType, stateUs,
                    sortBy, index, max)
        } else {
            state.value = ProposalListState.from(proposals!!.value!!)
        }
    }


    fun getQuery(): String? {
        return lastSearched
    }

    fun loadNewProposals(query: String?, gradeType: String?,
                         schoolType: String?, stateUs: String?,
                         sortBy: String?, index: String?, max: String?) {
        launch {
            state.value = LoadingState
            event.value = LoadingProposalsEvent(query)
            try {
                val result = donorRepository.getProposals(query, gradeType,
                        schoolType, stateUs, sortBy, index, max).await()
                val proposalsList = result.proposals
                total = result.totalProposals.toInt()
                currentIndex?.value = result.index.toInt()
                lastSearched = query
                lastIndex = proposalsList.lastIndex.toString()
                lastGradeType = gradeType
                lastMax = max
                lastSchoolType = schoolType
                lastState = stateUs
                lastSortBy = sortBy
                if (proposalsList.isEmpty()) state.value = EmptyListState

                proposals!!.value = proposalsList
                state.value = ProposalListState.from(proposals!!.value!!)

                event.value = LoadingProposalsEventEnded(query)
            } catch (error: Throwable) {
                event.value = LoadProposalsFailedEvent(query, error)
            }
        }
    }

    fun loadNextPage() {
        if (total!! >= lastIndex!!.toInt() + 2) {
            launch {

                event.value = LoadingProposalsEvent(lastSearched)
                state.value = LoadingMoreState

                try {
                    val result = donorRepository.getProposals(lastSearched, lastGradeType, lastSchoolType,
                            lastState, lastSortBy, lastIndex, lastMax).await()
                    val proposalsList = result.proposals
                    total = result.totalProposals.toInt()
                    currentIndex?.value = result.index.toInt()
                    lastIndex = (lastIndex!!.toInt() + lastMax!!.toInt()).toString()
                    if (proposalsList.isEmpty()) {
                        state.value = EmptyListState
                    } else {
                        proposals!!.value!!.addAll(proposalsList)
                        event.value = LoadMoreEvent(proposalsList.takeLast(proposalsList.size - 1) as MutableList<Proposal>)


                    }
                    event.value = LoadingProposalsEventEnded(lastSearched)


                } catch (error: Throwable) {
                    event.value = LoadProposalsFailedEvent(lastSearched, error)
                }
            }
        }
    }


    data class ProposalListState(
            val list: MutableList<Proposal>
    ) : State() {
        companion object {
            fun from(list: MutableList<Proposal>): ProposalListState {
                return when {
                    list.isEmpty() -> error(" list should not be empty")
                    else -> {
                        ProposalListState(list.takeLast(list.size) as MutableList<Proposal>)
                    }
                }
            }
        }
    }

    data class AppendedProposalListState(
            val list: MutableList<Proposal>
    ) : State() {
        companion object {
            fun from(list: MutableList<Proposal>): AppendedProposalListState {
                return when {
                    list.isEmpty() -> error(" list should not be empty")
                    else -> {
                        AppendedProposalListState(list)
                    }
                }
            }
        }
    }

    data class LoadingProposalsEvent(val query: String?) : Event()
    data class LoadProposalsFailedEvent(val query: String?, val error: Throwable) : Event()
    data class LoadingProposalsEventEnded(val query: String?) : Event()
    data class LoadMoreEvent(val list: MutableList<Proposal>) : Event()
}