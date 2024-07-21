package com.ahmed_apps.soufian_spending_tracker.analytics.data

import com.ahmed_apps.soufian_spending_tracker.analytics.domain.AnalyticsDataSource
import com.ahmed_apps.soufian_spending_tracker.analytics.domain.model.DailySpending
import com.ahmed_apps.soufian_spending_tracker.analytics.domain.model.WeeklySpending
import com.ahmed_apps.soufian_spending_tracker.core.domain.LocalSpendingDataSource
import java.time.LocalDate
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.WeekFields
import java.util.Locale

/**
 * @author Ahmed Guedmioui
 */
class AnalyticsDataSourceImpl(
    private val spendingDataSource: LocalSpendingDataSource,
) : AnalyticsDataSource {
    override suspend fun getSpendingByDay(): List<DailySpending> {
        val spendingList = spendingDataSource.getAllSpendingList()
        val allDates = addMissingDates(spendingDataSource.getAllDates())

        val dailySpendings = mutableListOf<DailySpending>()

        allDates.forEach { date ->
            val price = spendingList.filter { it.dateTimeUtc == date }.sumOf { it.price }
            val graphDate = date.format()
            dailySpendings.add(
                DailySpending(price = price, date = graphDate)
            )
        }

        return dailySpendings
    }

    override suspend fun getSpendingByWeek(): List<WeeklySpending> {
        val formatter = DateTimeFormatter.ofPattern("yyyy-M-d")
        val weekFields = WeekFields.of(Locale.getDefault())

        // Parse dates and group by week and year
        val weeklySpendingMap = getSpendingByDay()
            .map {
                val localDate = LocalDate.parse(it.date, formatter)
                val weekOfYear = localDate.get(weekFields.weekOfWeekBasedYear())
                val year = localDate.year
                Triple(weekOfYear, year, it.price)
            }
            .groupBy { it.first to it.second } // Group by (weekOfYear, year)
            .mapValues { entry ->
                entry.value.sumOf { it.third } // Sum the prices for each week
            }

        // Convert the map to a list of WeeklySpending
        return weeklySpendingMap.map { (weekYear, totalSpent) ->
            WeeklySpending(
                weekOfYear = weekYear.first,
                year = weekYear.second,
                totalSpent = totalSpent
            )
        }
    }


    private fun addMissingDates(allDates: List<ZonedDateTime>): List<ZonedDateTime> {
        if (allDates.isEmpty()) {
            return allDates
        }

        // Sort the dates to ensure they are in order
        val sortedDates = allDates.sorted()

        // List to hold all dates including missing ones
        val completeDates = mutableListOf<ZonedDateTime>()

        // Iterate through the list and find missing dates
        for (i in sortedDates.indices) {
            completeDates.add(sortedDates[i])
            if (i < sortedDates.size - 1) {
                var current = sortedDates[i]
                val next = sortedDates[i + 1]

                // Add missing dates between current and next date
                while (current.plusDays(1).toLocalDate() < next.toLocalDate()) {
                    current = current.plusDays(1)
                    completeDates.add(current)
                }
            }
        }

        return completeDates
    }

    private fun ZonedDateTime.format(): String {
        return "$year-$monthValue-$dayOfMonth"
    }
}



















