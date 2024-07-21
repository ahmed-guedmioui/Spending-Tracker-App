package com.ahmed_apps.soufian_spending_tracker.analytics.domain.model

/**
 * @author Ahmed Guedmioui
 */
data class WeeklySpending(
    val weekOfYear: Int,
    val year: Int,
    val totalSpent: Double
)
