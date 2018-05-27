package com.supporter.marcus.classsupport.data.remote.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class SchoolType
(@SerializedName("id")
 @Expose
 var id: Int? = null,
 @SerializedName("name")
 @Expose
 var name: String? = null)

