package com.ahmed_apps.soufian_spending_tracker.spending_details.di

import com.ahmed_apps.soufian_spending_tracker.spending_details.domian.usecase.SearchImagesUseCase
import com.ahmed_apps.soufian_spending_tracker.spending_details.domian.usecase.UpsertSpendingUseCase
import com.ahmed_apps.soufian_spending_tracker.spending_overview.presentation.SpendingOverviewViewModel
import com.ahmed_apps.soufian_spending_tracker.spending_details.presentation.SpendingDetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * @author Ahmed Guedmioui
 */

val spendingDetailsModule = module {
    single { SearchImagesUseCase(get()) }
    viewModel { SpendingOverviewViewModel(get(), get()) }
}














