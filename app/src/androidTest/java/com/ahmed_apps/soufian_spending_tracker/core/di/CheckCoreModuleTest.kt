package com.ahmed_apps.soufian_spending_tracker.core.di

import org.junit.Test
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.test.KoinTest
import org.koin.test.verify.verify

/**
 * @author Ahmed Guedmioui
 */
@OptIn(KoinExperimentalAPI::class)
class CheckCoreModuleTest : KoinTest {

    @Test
    fun checkAllModules() {
        coreModule.verify()
        coreTestModule.verify()
    }
}