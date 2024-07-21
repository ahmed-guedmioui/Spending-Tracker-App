package com.ahmed_apps.soufian_spending_tracker.spending_details.domian.util

/**
 * @author Ahmed Guedmioui
 */
sealed class Resource<T>(
    val data: T? = null,
) {
    class Success<T>(data: T?) : Resource<T>(data)
    class Error<T>(data: T? = null) : Resource<T>(data)
}