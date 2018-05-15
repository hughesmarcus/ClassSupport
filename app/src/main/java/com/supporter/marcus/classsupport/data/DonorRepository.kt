package com.supporter.marcus.classsupport.data

import com.supporter.marcus.classsupport.data.remote.DonorDataSource
import com.supporter.marcus.classsupport.data.remote.json.Proposal
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async

interface DonorRepository {
    fun getProposal(query:String): Deferred<List<Proposal>>

}

class DonorRepositoryImpl(
        private val donorDataSource: DonorDataSource
) : DonorRepository {
    override fun getProposal(query: String): Deferred<List<Proposal>>  = async{
        val proposals =  donorDataSource.searchProjects(query, "DONORSCHOOSE").await()
        val done = proposals.proposals
        done
    }
}