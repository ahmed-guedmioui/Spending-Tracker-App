package com.ahmed_apps.soufian_spending_tracker.core.data

import com.ahmed_apps.soufian_spending_tracker.core.domain.LocalSpendingDataSource
import com.ahmed_apps.soufian_spending_tracker.core.domain.model.Spending
import java.time.LocalDate
import java.time.ZonedDateTime


/**
 * @author Ahmed Guedmioui
 */
class FakeSpendingDataSource : LocalSpendingDataSource {

    private var fakeDatabase = mutableListOf<Spending>()

    init {
        for (i in 1..2) {
            val spending = Spending(
                id = i,
                name = "Category $i",
                imageUrl = "image $i",
                price = i.toDouble(),
                kilograms = i.toDouble(),
                quantity = i.toDouble(),
                dateTimeUtc = ZonedDateTime.parse("2023-07-18T10:15:30+01:00")
            )

            fakeDatabase.add(spending)
        }

        for (i in 3..4) {
            val spending = Spending(
                id = i,
                name = "Category $i",
                imageUrl = "image $i",
                price = i.toDouble(),
                kilograms = i.toDouble(),
                quantity = i.toDouble(),
                dateTimeUtc = ZonedDateTime.parse("2023-07-19T10:15:30+01:00")
            )

            fakeDatabase.add(spending)
        }


        for (i in 5..6) {
            val spending = Spending(
                id = i,
                name = "Category $i",
                imageUrl = "image $i",
                price = i.toDouble(),
                kilograms = i.toDouble(),
                quantity = i.toDouble(),
                dateTimeUtc = ZonedDateTime.parse("2023-07-21T18:15:30+01:00")
            )

            fakeDatabase.add(spending)
        }
    }

    override suspend fun getSpendingListByDate(
        dateTimeUtc: ZonedDateTime
    ): List<Spending> {
        return fakeDatabase.filter {
            it.dateTimeUtc.dayOfMonth == dateTimeUtc.dayOfMonth &&
                    it.dateTimeUtc.month == dateTimeUtc.month &&
                    it.dateTimeUtc.year == dateTimeUtc.year
        }
    }

    override suspend fun getAllSpendingList(): List<Spending> {
        return fakeDatabase
    }

    override suspend fun getAllDates(): List<ZonedDateTime> {
        val uniqueDates = mutableSetOf<LocalDate>()
        return fakeDatabase
            .map {
                it.dateTimeUtc
            }
            .filter { zonedDateTime ->
                val localDate = zonedDateTime.toLocalDate()
                uniqueDates.add(localDate)
            }
    }

    override suspend fun upsertSpending(spending: Spending) {
        val existingCategory = fakeDatabase.find { it.id == spending.id }
        if (existingCategory == null) {
            fakeDatabase.add(spending)
        } else {
            fakeDatabase.remove(existingCategory)
            fakeDatabase.add(spending)
        }
    }

    override suspend fun getSpending(id: Int): Spending? {
        return fakeDatabase.find { it.id == id }
    }

    override suspend fun getUsedBalance(): Double {
        return fakeDatabase.sumOf { it.price }
    }

    override suspend fun deleteSpending(id: Int) {
        fakeDatabase.removeIf { it.id == id }
    }
}








