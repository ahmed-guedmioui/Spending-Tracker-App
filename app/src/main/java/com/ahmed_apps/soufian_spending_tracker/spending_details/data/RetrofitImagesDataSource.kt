package com.ahmed_apps.soufian_spending_tracker.spending_details.data

import com.ahmed_apps.soufian_spending_tracker.spending_details.data.mappers.toImages
import com.ahmed_apps.soufian_spending_tracker.spending_details.data.remote.api.ImagesApi
import com.ahmed_apps.soufian_spending_tracker.spending_details.domian.RemoteImagesDataSource
import com.ahmed_apps.soufian_spending_tracker.spending_details.domian.model.Images

/**
 * @author Ahmed Guedmioui
 */
class RetrofitImagesDataSource(
    private val imagesApi: ImagesApi
): RemoteImagesDataSource {
    override suspend fun searchImages(query: String): Images? {
        return imagesApi.searchImages(query)?.toImages()
    }
}