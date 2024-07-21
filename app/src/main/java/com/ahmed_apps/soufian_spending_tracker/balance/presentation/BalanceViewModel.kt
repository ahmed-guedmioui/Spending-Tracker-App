package com.ahmed_apps.soufian_spending_tracker.balance.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmed_apps.soufian_spending_tracker.core.domain.CoreDataSource
import kotlinx.coroutines.launch

/**
 * @author Ahmed Guedmioui
 */
class BalanceViewModel(
    private val coreDataSource: CoreDataSource
) : ViewModel() {

    var state by mutableStateOf(BalanceState())
        private set

    init {
        viewModelScope.launch {
            state = state.copy(
                balance = coreDataSource.getBalance()
            )
        }
    }

    fun onAction(action: BalanceActions) {
        when (action) {
            is BalanceActions.OnBalanceChange -> {
                state = state.copy(
                    balance = action.newBalance
                )
            }

            BalanceActions.OnSaveBalance -> {
                viewModelScope.launch {
                    coreDataSource.updateBalance(state.balance)
                }
            }
        }
    }
}



















