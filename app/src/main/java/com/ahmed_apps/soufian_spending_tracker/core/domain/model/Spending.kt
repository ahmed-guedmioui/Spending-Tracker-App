package com.ahmed_apps.soufian_spending_tracker.core.domain.model

import java.time.ZonedDateTime

/**
 * @author Ahmed Guedmioui
 */
data class Spending(
    val id: Int?,
    val name: String,
    val imageUrl: String,
    val price: Double,
    val kilograms: Double,
    val quantity: Double,
    val dateTimeUtc: ZonedDateTime,
    val color: Int = 0xFF000000.toInt()
)






















