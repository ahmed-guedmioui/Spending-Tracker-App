package com.ahmed_apps.soufian_spending_tracker

import android.app.Application
import com.ahmed_apps.soufian_spending_tracker.analytics.di.analyticsModule
import com.ahmed_apps.soufian_spending_tracker.balance.di.balanceModule
import com.ahmed_apps.soufian_spending_tracker.spending_overview.di.spendingOverviewModule
import com.ahmed_apps.soufian_spending_tracker.core.di.coreModule
import com.ahmed_apps.soufian_spending_tracker.spending_details.di.spendingDetailsModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

/**
 * @author Ahmed Guedmioui
 */
class SpendingTrackerApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@SpendingTrackerApp)
            modules(
                coreModule,
                spendingOverviewModule,
                spendingDetailsModule,
                balanceModule,
                analyticsModule
            )
        }
    }
}