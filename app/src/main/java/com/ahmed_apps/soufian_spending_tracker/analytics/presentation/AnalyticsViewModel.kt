package com.ahmed_apps.soufian_spending_tracker.analytics.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmed_apps.soufian_spending_tracker.analytics.domain.AnalyticsDataSource
import com.ahmed_apps.soufian_spending_tracker.core.domain.LocalSpendingDataSource
import kotlinx.coroutines.launch

/**
 * @author Ahmed Guedmioui
 */
class AnalyticsViewModel(
    private val analyticsDataSource: AnalyticsDataSource,
    private val spendingDataSource: LocalSpendingDataSource
) : ViewModel() {

    var state by mutableStateOf(AnalyticsState())
        private set

    init {
        viewModelScope.launch {
            getAnalyticsData()
        }
    }

    suspend fun getAnalyticsData() {
        state = state.copy(
            usedBalance = spendingDataSource.getUsedBalance(),
            pricesPerDay = analyticsDataSource.getSpendingByDay()
                .map { it.price }.takeLast(8),
            datesPerDay = analyticsDataSource.getSpendingByDay()
                .map { it.date }.takeLast(8),
            pricesPerWeek = analyticsDataSource.getSpendingByWeek().map { it.totalSpent }
                .takeLast(8),
            datesPerWeek = analyticsDataSource.getSpendingByWeek().map {
                "${it.weekOfYear}/${it.year}"
            }.takeLast(8)
        )
    }

}



















