package com.supporter.marcus.classsupport.ui.search

import com.supporter.marcus.classsupport.data.remote.json.Proposal

data class ProposalItem(val id:String, val desc:String, val teacher:String, val costToComplete: String,
                        val school:String, val city: String, val state: String ) {
    companion object {
        fun from(proposal: Proposal ) = ProposalItem(
                proposal.id!!,
                proposal.shortDescription!!,
                proposal.teacherName!!,
                proposal.costToComplete!!,
                proposal.schoolName!!,
                proposal.city!!,
                proposal.state!!




        )
    }
}