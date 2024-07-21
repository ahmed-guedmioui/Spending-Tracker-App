package com.ahmed_apps.soufian_spending_tracker.core.domain

import com.ahmed_apps.soufian_spending_tracker.core.domain.model.Spending
import java.time.ZonedDateTime

interface LocalSpendingDataSource {
    suspend fun getSpendingListByDate(dateTimeUtc: ZonedDateTime): List<Spending>

    suspend fun getAllSpendingList(): List<Spending>

    suspend fun getAllDates(): List<ZonedDateTime>

    suspend fun upsertSpending(spending: Spending)

    suspend fun getSpending(id: Int): Spending?

    suspend fun getUsedBalance(): Double

    suspend fun deleteSpending(id: Int)
}