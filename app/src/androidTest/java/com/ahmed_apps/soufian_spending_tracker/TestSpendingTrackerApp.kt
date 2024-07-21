package com.ahmed_apps.soufian_spending_tracker

import android.app.Application
import androidx.test.platform.app.InstrumentationRegistry
import com.ahmed_apps.soufian_spending_tracker.spending_details.di.categoriesTestModule
import com.ahmed_apps.soufian_spending_tracker.core.di.coreTestModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.context.GlobalContext.stopKoin

/**
 * @author Ahmed Guedmioui
 */
class TestSpendingTrackerApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(
                InstrumentationRegistry.getInstrumentation().targetContext.applicationContext
            )
            modules(coreTestModule, categoriesTestModule)
        }
    }

    override fun onTerminate() {
        stopKoin()
        super.onTerminate()
    }
}







