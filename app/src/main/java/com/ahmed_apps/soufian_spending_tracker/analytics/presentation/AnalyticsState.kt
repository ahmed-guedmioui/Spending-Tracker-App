package com.ahmed_apps.soufian_spending_tracker.analytics.presentation


/**
 * @author Ahmed Guedmioui
 */
data class AnalyticsState(
    val pricesPerDay: List<Double> = emptyList(),
    val datesPerDay: List<String> = emptyList(),

    val pricesPerWeek: List<Double> = emptyList(),
    val datesPerWeek: List<String> = emptyList(),
    val usedBalance: Double = 0.0,
    val shouldShowDaysGraph: Boolean = false,
    val shouldShowWeeksGraph: Boolean = false,
)
