package com.ahmed_apps.soufian_spending_tracker.core.di

import androidx.room.Room
import com.ahmed_apps.soufian_spending_tracker.core.data.RoomSpendingDataSource
import com.ahmed_apps.soufian_spending_tracker.core.domain.LocalSpendingDataSource
import com.ahmed_apps.soufian_spending_tracker.spending_details.domian.usecase.UpsertSpendingUseCase
import com.ahmed_apps.soufian_spending_tracker.spending_overview.presentation.SpendingOverviewViewModel
import com.ahmed_apps.soufian_spending_tracker.spending_details.presentation.SpendingDetailsViewModel
import com.ahmed_apps.soufian_spending_tracker.core.data.FakeAndroidCoreDataSourceImpl
import com.ahmed_apps.soufian_spending_tracker.core.data.local.SpendingDatabase
import com.ahmed_apps.soufian_spending_tracker.core.domain.CoreDataSource
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module


/**
 * @author Ahmed Guedmioui
 */

val coreTestModule = module {
    single {
        Room.inMemoryDatabaseBuilder(
            androidApplication(),
            SpendingDatabase::class.java,
        ).build()
    }

    singleOf(::FakeAndroidCoreDataSourceImpl).bind<CoreDataSource>()

    single { get<SpendingDatabase>().spendingDao }

    singleOf(::RoomSpendingDataSource).bind<LocalSpendingDataSource>()

    single { UpsertSpendingUseCase(get()) }

    viewModel { SpendingOverviewViewModel(get(), get()) }

    viewModel { SpendingDetailsViewModel(get(), get(), get()) }

}












