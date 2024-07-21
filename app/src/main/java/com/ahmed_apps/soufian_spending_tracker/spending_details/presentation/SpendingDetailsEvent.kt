package com.ahmed_apps.soufian_spending_tracker.spending_details.presentation

/**
 * @author Ahmed Guedmioui
 */
sealed interface SpendingDetailsEvent {
    data object SaveSuccess : SpendingDetailsEvent
    data object SaveFailure : SpendingDetailsEvent
}