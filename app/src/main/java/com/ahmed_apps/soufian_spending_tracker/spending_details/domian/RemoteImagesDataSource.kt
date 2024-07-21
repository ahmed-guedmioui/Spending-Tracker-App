package com.ahmed_apps.soufian_spending_tracker.spending_details.domian

import com.ahmed_apps.soufian_spending_tracker.spending_details.domian.model.Images

/**
 * @author Ahmed Guedmioui
 */
interface RemoteImagesDataSource {
    suspend fun searchImages(query: String): Images?
}