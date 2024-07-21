package com.ahmed_apps.soufian_spending_tracker.core.data

import com.ahmed_apps.soufian_spending_tracker.core.domain.LocalSpendingDataSource
import com.ahmed_apps.soufian_spending_tracker.core.domain.model.Spending
import java.time.LocalDate
import java.time.ZonedDateTime
import kotlin.random.Random


/**
 * @author Ahmed Guedmioui
 */
class TestingSpendingDataSource : LocalSpendingDataSource {

    private var fakeDatabase = mutableListOf<Spending>()

    init {
        val random = Random.Default
        for (i in 1..9) {
            val spending = Spending(
                id = i,
                name = "Item $i",
                imageUrl = "https://img-cdn.pixlr.com/image-generator/history/65bb506dcb310754719cf81f/ede935de-1138-4f66-8ed7-44bd16efc709/medium.webp",
                price = random.nextInt(10).toDouble(),
                kilograms = i.toDouble(),
                quantity = i.toDouble(),
                dateTimeUtc = ZonedDateTime.parse("2024-07-0${i}T10:15:30+01:00")
            )

            fakeDatabase.add(spending)
        }
        for (i in 10..29) {
            val spending = Spending(
                id = i,
                name = "Item $i",
                imageUrl = "https://img-cdn.pixlr.com/image-generator/history/65bb506dcb310754719cf81f/ede935de-1138-4f66-8ed7-44bd16efc709/medium.webp",
                price = random.nextInt(10).toDouble(),
                kilograms = i.toDouble(),
                quantity = i.toDouble(),
                dateTimeUtc = ZonedDateTime.parse("2024-07-${i}T10:15:30+01:00")
            )

            fakeDatabase.add(spending)
        }

        for (i in 1..9) {
            val spending = Spending(
                id = i,
                name = "Item $i",
                imageUrl = "https://img-cdn.pixlr.com/image-generator/history/65bb506dcb310754719cf81f/ede935de-1138-4f66-8ed7-44bd16efc709/medium.webp",
                price = random.nextInt(10).toDouble(),
                kilograms = i.toDouble(),
                quantity = i.toDouble(),
                dateTimeUtc = ZonedDateTime.parse("2024-08-0${i}T10:15:30+01:00")
            )

            fakeDatabase.add(spending)
        }
        for (i in 10..29) {
            val spending = Spending(
                id = i,
                name = "Item $i",
                imageUrl = "https://img-cdn.pixlr.com/image-generator/history/65bb506dcb310754719cf81f/ede935de-1138-4f66-8ed7-44bd16efc709/medium.webp",
                price = random.nextInt(10).toDouble(),
                kilograms = i.toDouble(),
                quantity = i.toDouble(),
                dateTimeUtc = ZonedDateTime.parse("2024-08-${i}T10:15:30+01:00")
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







