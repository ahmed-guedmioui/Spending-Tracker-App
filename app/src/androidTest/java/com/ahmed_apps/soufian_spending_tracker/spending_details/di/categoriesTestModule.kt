package com.ahmed_apps.soufian_spending_tracker.spending_details.di

import com.ahmed_apps.soufian_spending_tracker.core.data.RoomSpendingDataSource
import com.ahmed_apps.soufian_spending_tracker.core.domain.LocalSpendingDataSource
import com.ahmed_apps.soufian_spending_tracker.spending_details.domian.usecase.UpsertSpendingUseCase
import com.ahmed_apps.soufian_spending_tracker.spending_overview.presentation.SpendingOverviewViewModel
import com.ahmed_apps.soufian_spending_tracker.spending_details.presentation.SpendingDetailsViewModel
import com.ahmed_apps.soufian_spending_tracker.core.data.local.SpendingDatabase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

/**
 * @author Ahmed Guedmioui
 */

val categoriesTestModule = module {

    single { get<SpendingDatabase>().spendingDao }

    singleOf(::RoomSpendingDataSource).bind<LocalSpendingDataSource>()

    single { UpsertSpendingUseCase(get()) }

    viewModel { SpendingOverviewViewModel(get(), get()) }

    viewModel { SpendingDetailsViewModel(get(), get(), get()) }

}














