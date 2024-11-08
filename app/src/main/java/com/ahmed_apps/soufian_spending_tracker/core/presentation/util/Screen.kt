package com.ahmed_apps.soufian_spending_tracker.core.presentation.util

/**
 * @author Ahmed Guedmioui
 */
sealed interface Screen {
    @kotlinx.serialization.Serializable
    data object SpendingOverview : Screen

    @kotlinx.serialization.Serializable
    data class SpendingDetails(val spendingId: Int = -1) : Screen

    @kotlinx.serialization.Serializable
    data object Balance : Screen

    @kotlinx.serialization.Serializable
    data object Analytics : Screen
}