package com.supporter.marcus.classsupport.ui.search

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.supporter.marcus.classsupport.data.DonorRepository
import com.supporter.marcus.classsupport.data.remote.json.Proposal
import com.supporter.marcus.classsupport.ui.Event
import com.supporter.marcus.classsupport.ui.LoadingMoreState
import com.supporter.marcus.classsupport.ui.State
import com.supporter.marcus.classsupport.util.mvvm.RxViewModel
import com.supporter.marcus.classsupport.util.mvvm.SingleLiveEvent
import com.supporter.marcus.classsupport.util.rx.SchedulerProvider



class SearchViewModel(
        private val donorRepository: DonorRepository,
        schedulerProvider: SchedulerProvider

) : RxViewModel(schedulerProvider) {
    private var lastSearched:String? =  null
    private var lastIndex:String? = "0"
    private var lastGradeType: String? = null
    private var lastSchoolType: String? = null
    private var lastState: String? = null
   private var lastSortBy: String? = null
    private var lastMax: String? = "50"
    private val mStates = MutableLiveData<State>()
    val states: LiveData<State>
        get() = mStates

    private val mEvents = SingleLiveEvent<Event>()
    val events: LiveData<Event>
        get() = mEvents

    fun getQuery(): String? {
        return lastSearched
    }
    fun loadNewProposals(query: String? ,gradeType: String?,
                         schoolType: String?,state: String?,
                         sortBy: String?,index: String?,max: String?) {
        launch {
            mEvents.value = LoadingProposalsEvent(query)
            try {
                val proposals = donorRepository.getProposals(query, gradeType, schoolType, state, sortBy, index, max).await()
                lastSearched = query
                lastIndex = proposals.lastIndex.toString()
                lastGradeType = gradeType
                lastMax = max
                lastSchoolType = schoolType
                lastState = state
                lastSortBy = sortBy
                mStates.value = ProposalListState.from(proposals)
                mEvents.value = LoadingProposalsEventEnded(query)
            } catch (error: Throwable) {
                mEvents.value = LoadProposalsFailedEvent(query, error)
            }
        }
    }

    fun loadNextPage() {
        launch {
            mEvents.value = LoadingProposalsEvent(lastSearched)
            mStates.value = LoadingMoreState
            try {
                val proposals = donorRepository.getProposals(lastSearched, lastGradeType, lastSchoolType,
                        lastState,lastSortBy,lastIndex,lastMax).await()
                lastIndex = (lastIndex!!.toInt()+ lastMax!!.toInt()).toString()
                mEvents.value = LoadingProposalsEventEnded(lastSearched)
                mStates.value = AppendedProposalListState.from(proposals)


            } catch (error: Throwable) {
                mEvents.value = LoadProposalsFailedEvent(lastSearched, error)
            }
        }
    }


    data class ProposalListState(
            val id: String,
            val first: Proposal,
            val lasts: MutableList<Proposal>
    ) : State() {
        companion object {
            fun from(list: MutableList<Proposal>): ProposalListState {
                return when {
                    list.isEmpty() -> error(" list should not be empty")
                    else -> {
                        val first = list.first()
                        val id = first.id
                        ProposalListState(id!!, first, list.takeLast(list.size - 1) as MutableList<Proposal>)
                    }
                }
            }
        }
    }
    data class AppendedProposalListState(
            val id: String,
            val first: Proposal,
            val lasts: MutableList<Proposal>
    ) : State() {
        companion object {
            fun from(list: MutableList<Proposal>): AppendedProposalListState {
                return when {
                    list.isEmpty() -> error(" list should not be empty")
                    else -> {
                        val first = list.first()
                        val id = first.id
                        AppendedProposalListState(id!!, first, list.takeLast(list.size - 1) as MutableList<Proposal>)
                    }
                }
            }
        }
    }

    data class LoadingProposalsEvent(val query: String?) : Event()
    data class LoadProposalsFailedEvent(val query: String?, val error: Throwable) : Event()
    data class LoadingProposalsEventEnded(val query: String?) : Event()
}