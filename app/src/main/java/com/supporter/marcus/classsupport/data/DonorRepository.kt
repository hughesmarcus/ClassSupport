package com.supporter.marcus.classsupport.data

import com.supporter.marcus.classsupport.data.remote.DonorDataSource
import com.supporter.marcus.classsupport.data.remote.json.Proposal
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async

interface DonorRepository {
    fun getProposal(query: String?, gradeType: String?,
                    schoolType: String?, state: String?,
                    sortBy: String?, index: String?, max: String?): Deferred<MutableList<Proposal>>

}

class DonorRepositoryImpl(
        private val donorDataSource: DonorDataSource
) : DonorRepository {
    override fun getProposal(query: String?, gradeType: String?,
                             schoolType: String?, state: String?,
                             sortBy: String?, index: String?, max: String?): Deferred<MutableList<Proposal>>  = async{
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