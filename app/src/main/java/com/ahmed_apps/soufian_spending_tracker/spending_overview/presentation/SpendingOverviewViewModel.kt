package com.ahmed_apps.soufian_spending_tracker.spending_overview.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmed_apps.soufian_spending_tracker.core.domain.LocalSpendingDataSource
import com.ahmed_apps.soufian_spending_tracker.core.domain.model.Spending
import com.ahmed_apps.soufian_spending_tracker.core.domain.CoreDataSource
import com.ahmed_apps.soufian_spending_tracker.spending_overview.presentation.util.randomColor
import kotlinx.coroutines.launch
import java.time.ZonedDateTime

/**
 * @author Ahmed Guedmioui
 */
class SpendingOverviewViewModel(
    private val spendingDataSource: LocalSpendingDataSource,
    private val coreDataSource: CoreDataSource
) : ViewModel() {

    var state by mutableStateOf(SpendingOverviewState())
        private set

    fun onAction(action: SpendingOverviewActions) {
        when (action) {
            SpendingOverviewActions.LoadSpendingOverviewAndBalance -> {
                loadSpendingListAndBalance()
            }

            is SpendingOverviewActions.OnDateChange -> {
                val newDate = state.allDates[action.newDateIndex]
                viewModelScope.launch {
                    state = state.copy(
                        pickedDate = newDate,
                        spendingList = getSpendingListByDate(newDate)
                    )
                }
            }

            is SpendingOverviewActions.OnDeleteSpending -> {
                viewModelScope.launch {
                    spendingDataSource.deleteSpending(action.categoryId)
                    state = state.copy(
                        spendingList = getSpendingListByDate(state.pickedDate),
                        allDates = spendingDataSource.getAllDates(),
                        balance = getBalance()
                    )
                }
            }
        }
    }

    private fun loadSpendingListAndBalance() {
        viewModelScope.launch {
            val allDates = spendingDataSource.getAllDates()
            state = state.copy(
                spendingList = getSpendingListByDate(
                    allDates.lastOrNull() ?: ZonedDateTime.now()
                ),
                balance = getBalance(),
                pickedDate = allDates.lastOrNull() ?: ZonedDateTime.now(),
                allDates = allDates.reversed()
            )
        }
    }

    private suspend fun getSpendingListByDate(date: ZonedDateTime): List<Spending> {
        return spendingDataSource
            .getSpendingListByDate(date)
            .reversed()
            .map {
                it.copy(color = randomColor().toArgb())
            }
    }

    private suspend fun getBalance(): Double {
        return coreDataSource.getBalance() - spendingDataSource.getUsedBalance()
    }

}



















