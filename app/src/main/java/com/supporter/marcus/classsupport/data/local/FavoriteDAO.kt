package com.supporter.marcus.classsupport.data.local

import android.arch.persistence.room.*

@Dao
interface FavoriteDAO {

    /**
     * Save entities
     */
    @Insert
    fun saveAll(entities: List<ProposalEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(entity: ProposalEntity)


    @Delete()
    fun removeFavorite(entity: ProposalEntity)


    /**
     * Find ProposalEntity for given id
     * @return ProposalEntity
     */
    @Query("SELECT * FROM proposal WHERE id = :id")
    fun findProposalById(id: String): ProposalEntity?


    @Query("SELECT * FROM proposal")
    fun getProposals(): List<ProposalEntity>
}