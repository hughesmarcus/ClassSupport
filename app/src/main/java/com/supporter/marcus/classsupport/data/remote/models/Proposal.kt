package com.supporter.marcus.classsupport.data.remote.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class Proposal(

        @SerializedName("id")
        @Expose
        var id: String? = null,
        @SerializedName("synopsis")
        @Expose
        var synopsis: String? = null,
        @SerializedName("hashCode")
        @Expose
        var hashCode: String? = null,
        @SerializedName("proposalURL")
        @Expose
        var proposalURL: String? = null,
        @SerializedName("fundURL")
        @Expose
        var fundURL: String? = null,
        @SerializedName("imageURL")
        @Expose
        var imageURL: String? = null,
        @SerializedName("retinaImageURL")
        @Expose
        var retinaImageURL: String? = null,
        @SerializedName("thumbImageURL")
        @Expose
        var thumbImageURL: String? = null,
        @SerializedName("title")
        @Expose
        var title: String? = null,
        @SerializedName("shortDescription")
        @Expose
        var shortDescription: String? = null,
        @SerializedName("fulfillmentTrailer")
        @Expose
        var fulfillmentTrailer: String? = null,
        @SerializedName("snippets")
        @Expose
        var snippets: List<Any>? = null,
        @SerializedName("percentFunded")
        @Expose
        var percentFunded: String? = null,
        @SerializedName("numDonors")
        @Expose
        var numDonors: String? = null,
        @SerializedName("costToComplete")
        @Expose
        var costToComplete: String? = null,
        @SerializedName("studentLed")
        @Expose
        var studentLed: Boolean? = null,
        @SerializedName("numStudents")
        @Expose
        var numStudents: String? = null,
        @SerializedName("professionalDevelopment")
        @Expose
        var professionalDevelopment: Boolean? = null,
        @SerializedName("matchingFund")
        @Expose
        var matchingFund: MatchingFund? = null,
        @SerializedName("totalPrice")
        @Expose
        var totalPrice: String? = null,
        @SerializedName("freeShipping")
        @Expose
        var freeShipping: String? = null,
        @SerializedName("teacherId")
        @Expose
        var teacherId: String? = null,
        @SerializedName("teacherName")
        @Expose
        var teacherName: String? = null,
        @SerializedName("gradeLevel")
        @Expose
        var gradeLevel: GradeLevel? = null,
        @SerializedName("povertyLevel")
        @Expose
        var povertyLevel: String? = null,
        @SerializedName("povertyType")
        @Expose
        var povertyType: PovertyType? = null,
        @SerializedName("teacherTypes")
        @Expose
        var teacherTypes: List<Any>? = null,
        @SerializedName("schoolTypes")
        @Expose
        var schoolTypes: List<SchoolType>? = null,
        @SerializedName("schoolName")
        @Expose
        var schoolName: String? = null,
        @SerializedName("schoolUrl")
        @Expose
        var schoolUrl: String? = null,
        @SerializedName("city")
        @Expose
        var city: String? = null,
        @SerializedName("zip")
        @Expose
        var zip: String? = null,
        @SerializedName("state")
        @Expose
        var state: String? = null,
        @SerializedName("stateFullName")
        @Expose
        var stateFullName: String? = null,
        @SerializedName("latitude")
        @Expose
        var latitude: String? = null,
        @SerializedName("longitude")
        @Expose
        var longitude: String? = null,
        @SerializedName("zone")
        @Expose
        var zone: Zone? = null,
        @SerializedName("subject")
        @Expose
        var subject: Subject? = null,
        @SerializedName("resource")
        @Expose
        var resource: Resource? = null,
        @SerializedName("expirationDate")
        @Expose
        var expirationDate: String? = null,
        @SerializedName("fundingStatus")
        @Expose
        var fundingStatus: String? = null

)