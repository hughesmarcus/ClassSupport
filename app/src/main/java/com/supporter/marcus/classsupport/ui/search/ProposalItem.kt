package com.supporter.marcus.classsupport.ui.search

import com.supporter.marcus.classsupport.data.local.ProposalEntity
import com.supporter.marcus.classsupport.data.remote.json.Proposal

data class ProposalItem(val id: String, val desc: String, val teacher: String, val costToComplete: String,
                        val school: String, val city: String, val state: String,
                        val imageUrl: String, val title: String,
                        val totalPrice: String, val prefunded: String, val donors: String,
                        val synopsis: String) {
    companion object {
        fun from(proposal: Proposal) = ProposalItem(
                proposal.id!!,
                proposal.shortDescription!!,
                proposal.teacherName!!,
                proposal.costToComplete!!,
                proposal.schoolName!!,
                proposal.city!!,
                proposal.state!!,
                proposal.retinaImageURL!!,
                proposal.title!!,
                proposal.totalPrice!!,
                proposal.percentFunded!!,
                proposal.numDonors!!,
                proposal.synopsis!!

        )

        fun from(proposal: ProposalEntity) = ProposalItem(
                proposal.id,
                proposal.desc,
                proposal.teacher,
                proposal.costToComplete,
                proposal.school,
                proposal.city,
                proposal.state,
                proposal.imageUrl,
                proposal.title,
                proposal.totalPrice,
                proposal.prefunded,
                proposal.donors,
                proposal.synopsis

        )
    }
}