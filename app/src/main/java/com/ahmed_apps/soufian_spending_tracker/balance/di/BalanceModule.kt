package com.ahmed_apps.soufian_spending_tracker.balance.di

import com.ahmed_apps.soufian_spending_tracker.balance.presentation.BalanceViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * @author Ahmed Guedmioui
 */
val balanceModule = module {
    viewModel { BalanceViewModel(get()) }
}