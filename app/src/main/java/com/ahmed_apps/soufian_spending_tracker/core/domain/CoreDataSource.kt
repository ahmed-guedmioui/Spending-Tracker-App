package com.ahmed_apps.soufian_spending_tracker.core.domain

/**
 * @author Ahmed Guedmioui
 */
interface CoreDataSource {
    suspend fun updateBalance(balance: Double)
    suspend fun getBalance(): Double
}