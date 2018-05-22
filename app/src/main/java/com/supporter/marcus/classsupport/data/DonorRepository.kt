package com.supporter.marcus.classsupport.data

import android.arch.lifecycle.MutableLiveData
import com.supporter.marcus.classsupport.data.remote.DonorDataSource
import com.supporter.marcus.classsupport.data.remote.json.Proposal
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async

interface DonorRepository {
    fun getProposals(query: String?, gradeType: String?,
                     schoolType: String?, state: String?,
                     sortBy: String?, index: String?, max: String?): Deferred<MutableList<Proposal>>

    fun getProposalById(id: String?): Deferred<Proposal>

}

class DonorRepositoryImpl(
        private val donorDataSource: DonorDataSource
) : DonorRepository {
    override fun getProposalById(id: String?): Deferred<Proposal> = async {
        val proposal = donorDataSource.getProjectById(id, "DONORSCHOOSE", "true").await()
        val done = proposal.proposals[0]
        done
    }

    override fun getProposals(query: String?, gradeType: String?,
                              schoolType: String?, state: String?,
                              sortBy: String?, index: String?, max: String?): Deferred<MutableList<Proposal>> = async {
        val proposals =  donorDataSource.searchProjects(
                query,
                "DONORSCHOOSE",
                gradeType,
                schoolType,
                state,
                sortBy,
                index,
                max
        ).await()
        val done = proposals.proposals
        done
    }
}