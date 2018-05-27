package com.supporter.marcus.classsupport.data.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

@Database(entities = [ProposalEntity::class], version = 1)

abstract class ProposalDatabase : RoomDatabase() {

    abstract fun favoriteDAO(): FavoriteDAO
    abstract fun proposalDAO(): ProposalDAO
}