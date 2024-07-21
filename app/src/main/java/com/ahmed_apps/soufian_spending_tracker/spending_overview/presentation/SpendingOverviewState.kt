package com.ahmed_apps.soufian_spending_tracker.spending_overview.presentation

import com.ahmed_apps.soufian_spending_tracker.core.domain.model.Spending
import java.time.ZonedDateTime

/**
 * @author Ahmed Guedmioui
 */
data class SpendingOverviewState(
    val spendingList: List<Spending> = emptyList(),
    val balance: Double = 0.0,
    val pickedDate: ZonedDateTime = ZonedDateTime.now(),
    val allDates: List<ZonedDateTime> = emptyList(),
    val isDatePickerDialogVisible: Boolean = false
)
