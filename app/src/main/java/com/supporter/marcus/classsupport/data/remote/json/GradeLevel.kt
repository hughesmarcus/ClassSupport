package com.supporter.marcus.classsupport.data.remote.json

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class GradeLevel(

    @SerializedName("id")
    @Expose
    var id: String? = null,
    @SerializedName("name")
    @Expose
    var name: String? = null
){}