package com.supporter.marcus.classsupport.data.remote.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class PovertyType(

        @SerializedName("label")
        @Expose
        var label: String? = null,
        @SerializedName("name")
        @Expose
        var name: String? = null,
        @SerializedName("range")
        @Expose
        var range: String? = null,
        @SerializedName("showPovertyLevel")
        @Expose
        var showPovertyLevel: String? = null
)
