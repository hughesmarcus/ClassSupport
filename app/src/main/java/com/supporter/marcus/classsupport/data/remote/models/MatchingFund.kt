package com.supporter.marcus.classsupport.data.remote.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class MatchingFund(

        @SerializedName("matchingKey")
        @Expose
        var matchingKey: String? = null,
        @SerializedName("ownerRegion")
        @Expose
        var ownerRegion: String? = null,
        @SerializedName("name")
        @Expose
        var name: String? = null,
        @SerializedName("donorSalutation")
        @Expose
        var donorSalutation: String? = null,
        @SerializedName("type")
        @Expose
        var type: String? = null,
        @SerializedName("matchImpactMultiple")
        @Expose
        var matchImpactMultiple: String? = null,
        @SerializedName("multipleForDisplay")
        @Expose
        var multipleForDisplay: String? = null,
        @SerializedName("logoURL")
        @Expose
        var logoURL: String? = null,
        @SerializedName("faqURL")
        @Expose
        var faqURL: String? = null,
        @SerializedName("amount")
        @Expose
        var amount: String? = null,
        @SerializedName("description")
        @Expose
        var description: String? = null

)