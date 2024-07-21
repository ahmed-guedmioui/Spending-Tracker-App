package com.ahmed_apps.soufian_spending_tracker.balance.presentation

/**
 * @author Ahmed Guedmioui
 */
sealed interface BalanceActions {
    data class OnBalanceChange(val newBalance: Double) : BalanceActions
    data object OnSaveBalance : BalanceActions
}
