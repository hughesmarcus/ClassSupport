package com.supporter.marcus.classsupport.ui.detail

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.supporter.marcus.classsupport.data.remote.json.Proposal
import com.supporter.marcus.classsupport.ui.Event
import com.supporter.marcus.classsupport.ui.State
import com.supporter.marcus.classsupport.util.mvvm.RxViewModel
import com.supporter.marcus.classsupport.util.mvvm.SingleLiveEvent
import com.supporter.marcus.classsupport.data.DonorRepository
import com.supporter.marcus.classsupport.util.rx.SchedulerProvider

class ProposalDetailViewModel(private val donorRepository: DonorRepository, schedulerProvider: SchedulerProvider) : RxViewModel(schedulerProvider) {
    private var proposal: MutableLiveData<Proposal>? = null
    fun getProposal(id: String): LiveData<Proposal> {
        if (proposal == null) {
            proposal = MutableLiveData<Proposal>()
            loadProposal(id)
        }
        return proposal as MutableLiveData<Proposal>
    }

    private fun loadProposal(id: String) {
        launch {
            val proposalresult = donorRepository.getProposalById(id).await()
            proposal!!.value = proposalresult
        }

    }

}