package com.ahmed_apps.soufian_spending_tracker.core.data

import com.ahmed_apps.soufian_spending_tracker.core.data.local.SpendingDao
import com.ahmed_apps.soufian_spending_tracker.core.data.mappers.toSpending
import com.ahmed_apps.soufian_spending_tracker.core.data.mappers.toNewSpendingEntity
import com.ahmed_apps.soufian_spending_tracker.core.data.mappers.toUpdatedSpendingEntity
import com.ahmed_apps.soufian_spending_tracker.core.domain.LocalSpendingDataSource
import com.ahmed_apps.soufian_spending_tracker.core.domain.model.Spending
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime

/**
 * @author Ahmed Guedmioui
 */
class RoomSpendingDataSource(
    private val spendingDao: SpendingDao
) : LocalSpendingDataSource {

    override suspend fun getSpendingListByDate(
        dateTimeUtc: ZonedDateTime
    ): List<Spending> {
        return spendingDao.getAllCategories()
            .map {
                it.toSpending()
            }
            .filter {
                it.dateTimeUtc.dayOfMonth == dateTimeUtc.dayOfMonth &&
                        it.dateTimeUtc.month == dateTimeUtc.month &&
                        it.dateTimeUtc.year == dateTimeUtc.year
            }
    }

    override suspend fun getAllSpendingList(): List<Spending> {
        return spendingDao.getAllCategories().map { it.toSpending() }
    }

    override suspend fun getAllDates(): List<ZonedDateTime> {
        val uniqueDates = mutableSetOf<LocalDate>()
        return spendingDao.getAllDates()
            .map {
                Instant.parse(it).atZone(ZoneId.of("UTC"))
            }
            .filter { zonedDateTime ->
                val localDate = zonedDateTime.toLocalDate()
                uniqueDates.add(localDate)
            }
    }

    override suspend fun upsertSpending(spending: Spending) {
        spendingDao.upsertSpending(
            if (spending.id == null) {
                spending.toNewSpendingEntity()
            } else {
                spending.toUpdatedSpendingEntity()
            }
        )
    }

    override suspend fun getSpending(id: Int): Spending? {
        return spendingDao.getSpending(id).toSpending()
    }

    override suspend fun getUsedBalance(): Double {
        return spendingDao.getUsedBalance() ?: 0.0
    }

    override suspend fun deleteSpending(id: Int) {
        spendingDao.deleteSpending(id)
    }
}