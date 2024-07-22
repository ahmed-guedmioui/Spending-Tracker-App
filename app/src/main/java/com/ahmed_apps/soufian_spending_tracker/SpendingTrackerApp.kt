package com.ahmed_apps.soufian_spending_tracker

import android.app.Application
import com.ahmed_apps.soufian_spending_tracker.analytics.di.analyticsModule
import com.ahmed_apps.soufian_spending_tracker.balance.di.balanceModule
import com.ahmed_apps.soufian_spending_tracker.spending_overview.di.spendingOverviewModule
import com.ahmed_apps.soufian_spending_tracker.core.di.coreModule
import com.ahmed_apps.soufian_spending_tracker.spending_details.di.spendingDetailsModule
import com.ironsource.mediationsdk.IronSource
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

/**
 * @author Ahmed Guedmioui
 */
class SpendingTrackerApp : Application() {

    companion object {
        const val IRON_SOURCE_APP_KEY = "xxx"
        const val IRON_SOURCE_INTER_ID = "xxx"
    }

    override fun onCreate() {
        super.onCreate()

        IronSource.init(this, IRON_SOURCE_APP_KEY, IronSource.AD_UNIT.INTERSTITIAL)

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