package com.ahmed_apps.soufian_spending_tracker.analytics.di

import com.ahmed_apps.soufian_spending_tracker.analytics.data.AnalyticsDataSourceImpl
import com.ahmed_apps.soufian_spending_tracker.analytics.domain.AnalyticsDataSource
import com.ahmed_apps.soufian_spending_tracker.analytics.presentation.AnalyticsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * @author Ahmed Guedmioui
 */
val analyticsModule = module {
    single<AnalyticsDataSource> { AnalyticsDataSourceImpl(get()) }
    viewModel { AnalyticsViewModel(get(), get()) }
}