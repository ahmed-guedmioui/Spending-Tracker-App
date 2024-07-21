package com.ahmed_apps.soufian_spending_tracker.core.data

import android.content.SharedPreferences
import com.ahmed_apps.soufian_spending_tracker.core.domain.CoreDataSource

/**
 * @author Ahmed Guedmioui
 */
class CoreDataSourceImpl(
    private val prefs: SharedPreferences
) : CoreDataSource {

    override suspend fun updateBalance(balance: Double) {
        prefs.edit().putFloat(KEY_BALANCE, balance.toFloat()).apply()
    }

    override suspend fun getBalance(): Double {
        return prefs.getFloat(KEY_BALANCE, 0f).toDouble()
    }

    companion object {
        private const val KEY_BALANCE = "KEY_BALANCE"
    }
}











