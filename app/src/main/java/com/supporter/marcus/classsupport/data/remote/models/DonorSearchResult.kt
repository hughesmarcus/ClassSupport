package com.supporter.marcus.classsupport.data.remote.models

import android.arch.persistence.room.Entity
import com.google.gson.annotations.SerializedName


@Entity(primaryKeys = ["query"])
data class DonorSearchResult(
        @SerializedName("searchTerms")
        var searchTerms: String,
        @SerializedName("searchURL")

        var searchURL: String,
        @SerializedName("totalProposals")

        var totalProposals: String,
        @SerializedName("index")

        var index: String,
        @SerializedName("max")

        var max: String,
        @SerializedName("breadcrumb")

        var breadcrumb: List<List<String>>,
        @SerializedName("proposals")

        var proposals: MutableList<Proposal>
)
