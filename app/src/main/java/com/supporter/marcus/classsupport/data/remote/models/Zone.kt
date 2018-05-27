package com.supporter.marcus.classsupport.data.remote.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class Zone
(
        @SerializedName("id")
        @Expose
        var id: String? = null,
        @SerializedName("name")
        @Expose
        var name: String? = null)

