package com.ahmed_apps.soufian_spending_tracker.spending_overview.di

import com.ahmed_apps.soufian_spending_tracker.spending_details.domian.usecase.UpsertSpendingUseCase
import com.ahmed_apps.soufian_spending_tracker.spending_details.presentation.SpendingDetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * @author Ahmed Guedmioui
 */

val spendingOverviewModule = module {
    single { UpsertSpendingUseCase(get()) }
    viewModel { SpendingDetailsViewModel(get(), get(), get()) }
}














