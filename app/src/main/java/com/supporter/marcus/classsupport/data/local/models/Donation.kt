package com.supporter.marcus.classsupport.data.local.models

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*

@Entity(tableName = "donations")
data class Donation(
        @PrimaryKey
        val id: String,
        val amount: Double,
        val date: Date,
        val name: String
)