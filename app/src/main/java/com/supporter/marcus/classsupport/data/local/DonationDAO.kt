package com.supporter.marcus.classsupport.data.local

import android.arch.persistence.room.*
import com.supporter.marcus.classsupport.data.local.models.Donation

@Dao
interface DonationDAO {

    /**
     * Save donations
     */
    @Insert
    fun saveAll(donations: List<Donation>)

    /**
     * Save single donation
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(donation: Donation)

    /**
     * Delete single donation
     */
    @Delete()
    fun removeDonation(donation: Donation)


    /**
     * Find donation for given id
     * @return Donation
     */
    @Query("SELECT * FROM donations WHERE id = :id")
    fun findDonationById(id: String): Donation?

    /**
     * Get all donations
     * @return List<Donation>
     */
    @Query("SELECT * FROM donations")
    fun getDonations(): List<Donation>
}