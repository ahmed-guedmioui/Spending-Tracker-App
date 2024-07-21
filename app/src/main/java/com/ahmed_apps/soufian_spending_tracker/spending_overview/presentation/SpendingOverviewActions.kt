package com.ahmed_apps.soufian_spending_tracker.spending_overview.presentation

/**
 * @author Ahmed Guedmioui
 */
sealed interface SpendingOverviewActions {
    data object LoadSpendingOverviewAndBalance : SpendingOverviewActions
    data class OnDateChange(val newDateIndex: Int) : SpendingOverviewActions
    data class OnDeleteSpending(val categoryId: Int) : SpendingOverviewActions
}
