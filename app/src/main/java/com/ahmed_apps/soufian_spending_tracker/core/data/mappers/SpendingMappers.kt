package com.ahmed_apps.soufian_spending_tracker.core.data.mappers

import com.ahmed_apps.soufian_spending_tracker.core.data.local.SpendingEntity
import com.ahmed_apps.soufian_spending_tracker.core.domain.model.Spending
import java.time.Instant
import java.time.ZoneId

/**
 * @author Ahmed Guedmioui
 */

fun SpendingEntity.toSpending(): Spending = Spending(
    id = id ?: 0,
    name = name,
    imageUrl = imageUrl,
    price = price,
    kilograms = kilograms,
    quantity = quantity,
    dateTimeUtc = Instant.parse(dateTimeUtc).atZone(ZoneId.of("UTC"))
)

fun Spending.toNewSpendingEntity(): SpendingEntity = SpendingEntity(
    name = name,
    imageUrl = imageUrl,
    price = price,
    kilograms = kilograms,
    quantity = quantity,
    dateTimeUtc = dateTimeUtc.toInstant().toString()
)

fun Spending.toUpdatedSpendingEntity(): SpendingEntity = SpendingEntity(
    id = id,
    name = name,
    imageUrl = imageUrl,
    price = price,
    kilograms = kilograms,
    quantity = quantity,
    dateTimeUtc = dateTimeUtc.toInstant().toString()
)











