package com.supporter.marcus.classsupport.data.local

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.supporter.marcus.classsupport.ui.search.ProposalItem

@Entity(tableName = "proposal")
data class ProposalEntity(
        @PrimaryKey
        val id: String,
        val desc: String,
        val teacher: String,
        val costToComplete: String,
        val school: String,
        val city: String,
        val state: String,
        val imageUrl: String,
        val title: String,
        val totalPrice: String,
        val prefunded: String,
        val donors: String,
        val synopsis: String
) {

    companion object {

        fun from(proposal: ProposalItem) = ProposalEntity(
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