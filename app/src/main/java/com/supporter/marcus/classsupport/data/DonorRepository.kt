package com.supporter.marcus.classsupport.data

import com.supporter.marcus.classsupport.data.local.DonationDAO
import com.supporter.marcus.classsupport.data.local.FavoriteDAO
import com.supporter.marcus.classsupport.data.local.models.Donation
import com.supporter.marcus.classsupport.data.local.models.ProposalEntity
import com.supporter.marcus.classsupport.data.remote.DonorDataSource
import com.supporter.marcus.classsupport.data.remote.models.DonorSearchResult
import com.supporter.marcus.classsupport.data.remote.models.Proposal
import com.supporter.marcus.classsupport.ui.search.ProposalItem
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch


interface DonorRepository {
    fun getProposals(query: String?, gradeType: String?,
                     schoolType: String?, state: String?,
                     sortBy: String?, index: String?, max: String?): Deferred<DonorSearchResult>

    fun getProposalById(id: String?): Deferred<Proposal>

    fun getFavorites(): Deferred<MutableList<ProposalItem>>

    fun getFavoriteById(id: String): Deferred<Boolean>

    fun addFavorite(proposalItem: ProposalItem)

    fun removeFavorite(proposalItem: ProposalItem)

    fun getDonations(): Deferred<List<Donation>>

    fun getDonationById(id: String): Deferred<Donation?>

    fun addDonation(donation: Donation)

    fun removeDonation(donation: Donation)
}

class DonorRepositoryImpl(
        private val donorDataSource: DonorDataSource,
        private val favoriteDAO: FavoriteDAO,
        private val donationDAO: DonationDAO
) : DonorRepository {
    override fun getDonations(): Deferred<List<Donation>> = async {
        (donationDAO.getDonations())
    }

    override fun getDonationById(id: String): Deferred<Donation?> = async {
        (donationDAO.findDonationById(id))
    }

    override fun addDonation(donation: Donation) {
        launch { donationDAO.save(donation) }
    }

    override fun removeDonation(donation: Donation) {
        launch { donationDAO.removeDonation(donation) }
    }

    override fun getFavoriteById(id: String): Deferred<Boolean> = async {
        (favoriteDAO.findProposalById(id) != null)
    }

    override fun addFavorite(proposalItem: ProposalItem) {

        launch { favoriteDAO.save(ProposalEntity.from(proposalItem)) }

    }

    override fun removeFavorite(proposalItem: ProposalItem) {
        launch { favoriteDAO.removeFavorite(ProposalEntity.from(proposalItem)) }

    }

    override fun getFavorites(): Deferred<MutableList<ProposalItem>> = async {
        val proposals = favoriteDAO.getProposals().map { ProposalItem.from(it) } as MutableList
        proposals
    }

    override fun getProposalById(id: String?): Deferred<Proposal> = async {
        val proposal = donorDataSource.getProjectById(id, "DONORSCHOOSE", "true").await()
        val done = proposal.proposals[0]
        done
    }

    override fun getProposals(query: String?, gradeType: String?,
                              schoolType: String?, state: String?,
                              sortBy: String?, index: String?, max: String?): Deferred<DonorSearchResult> = async {

        val proposals = donorDataSource.searchProjects(
                query,
                "DONORSCHOOSE",
                gradeType,
                schoolType,
                state,
                sortBy,
                index,
                max,
                "true"
        ).await()
        proposals
    }

}