package com.ahmed_apps.soufian_spending_tracker.spending_overview.presentation.util

import java.time.ZonedDateTime

/**
 * @author Ahmed Guedmioui
 */

fun ZonedDateTime.format(): String {
   return "$dayOfMonth-$monthValue-$year"
}