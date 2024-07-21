package com.ahmed_apps.soufian_spending_tracker.analytics.domain

import com.ahmed_apps.soufian_spending_tracker.analytics.domain.model.DailySpending
import com.ahmed_apps.soufian_spending_tracker.analytics.domain.model.WeeklySpending

/**
 * @author Ahmed Guedmioui
 */
interface AnalyticsDataSource {
    suspend fun getSpendingByDay(): List<DailySpending>
    suspend fun getSpendingByWeek(): List<WeeklySpending>
}