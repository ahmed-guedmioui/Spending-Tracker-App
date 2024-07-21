package com.ahmed_apps.soufian_spending_tracker.core.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

/**
 * @author Ahmed Guedmioui
 */
@Dao
interface SpendingDao {
    @Upsert
    suspend fun upsertSpending(entity: SpendingEntity)

    @Query("SELECT * FROM spendingentity")
    suspend fun getAllCategories(): List<SpendingEntity>

    @Query("SELECT dateTimeUtc FROM SpendingEntity")
    suspend fun getAllDates(): List<String>

    @Query("SELECT * FROM spendingEntity WHERE id = :id")
    suspend fun getSpending(id: Int): SpendingEntity

    @Query("SELECT SUM(price) FROM spendingentity")
    suspend fun getUsedBalance(): Double?

    @Query("DELETE FROM spendingentity WHERE id = :id")
    suspend fun deleteSpending(id: Int)
}













