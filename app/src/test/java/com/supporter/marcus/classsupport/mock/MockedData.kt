package com.supporter.marcus.classsupport.mock

import com.supporter.marcus.classsupport.data.remote.models.Proposal

object MockedData {
    val mockList = mutableListOf<Proposal>(
            Proposal("hello"),
            Proposal("proposal2"),
            Proposal("proposal3"),
            Proposal("proposal4")
    )
}