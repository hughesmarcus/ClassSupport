package com.supporter.marcus.classsupport.data.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.supporter.marcus.classsupport.data.DateTypeConverter
import com.supporter.marcus.classsupport.data.local.models.Donation
import com.supporter.marcus.classsupport.data.local.models.ProposalEntity

@Database(entities = [ProposalEntity::class, Donation::class], version = 2)
@TypeConverters(DateTypeConverter::class)
abstract class ProposalDatabase : RoomDatabase() {

    abstract fun favoriteDAO(): FavoriteDAO
    abstract fun proposalDAO(): ProposalDAO
    abstract fun donationDAO(): DonationDAO
}