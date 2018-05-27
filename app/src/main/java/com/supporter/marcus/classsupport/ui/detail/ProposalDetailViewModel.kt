package com.supporter.marcus.classsupport.ui.detail

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.supporter.marcus.classsupport.data.DonorRepository
import com.supporter.marcus.classsupport.data.remote.models.Proposal
import com.supporter.marcus.classsupport.ui.search.ProposalItem
import com.supporter.marcus.classsupport.util.mvvm.RxViewModel
import com.supporter.marcus.classsupport.util.rx.SchedulerProvider

class ProposalDetailViewModel(private val donorRepository: DonorRepository, schedulerProvider: SchedulerProvider) : RxViewModel(schedulerProvider) {
    private var proposal: MutableLiveData<Proposal>? = null
    private var isFav: MutableLiveData<Boolean>? = null
    fun getProposal(id: String): LiveData<Proposal> {
        if (proposal == null) {
            proposal = MutableLiveData<Proposal>()
            loadProposal(id)
        }
        return proposal as MutableLiveData<Proposal>
    }

    private fun loadProposal(id: String) {
        launch {
            val proposalResult = donorRepository.getProposalById(id).await()
            proposal!!.value = proposalResult
        }

    }

    fun addFavorite(proposalItem: ProposalItem) {

        donorRepository.addFavorite(proposalItem)
    }

    fun removeFavorite(proposalItem: ProposalItem) {
        donorRepository.removeFavorite(proposalItem)
    }

    fun getFavorite(id: String): LiveData<Boolean> {

        if (isFav == null) {
            isFav = MutableLiveData<Boolean>()
            loadFavorite(id)
        }

        return isFav as MutableLiveData<Boolean>
    }

    private fun loadFavorite(id: String) {
        launch {
            val favorite = donorRepository.getFavoriteById(id).await()
            isFav!!.value = favorite
        }

    }
}