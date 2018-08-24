package com.supporter.marcus.classsupport.data.local.models

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*

@Entity(tableName = "donations")
data class Donation(
        @PrimaryKey(autoGenerate = true)
        val id: Int,
        val amount: Float,
        val date: Date,
        val name: String
)