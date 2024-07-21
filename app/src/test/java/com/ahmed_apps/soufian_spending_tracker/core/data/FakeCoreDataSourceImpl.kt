package com.ahmed_apps.soufian_spending_tracker.core.data

import com.ahmed_apps.soufian_spending_tracker.core.domain.CoreDataSource


/**
 * @author Ahmed Guedmioui
 */
class FakeCoreDataSourceImpl : CoreDataSource {

   private var usreBalanace = 0.0f

    override suspend fun updateBalance(balance: Double) {
        usreBalanace = balance.toFloat()
    }

    override suspend fun getBalance(): Double {
        return usreBalanace.toDouble()
    }
}