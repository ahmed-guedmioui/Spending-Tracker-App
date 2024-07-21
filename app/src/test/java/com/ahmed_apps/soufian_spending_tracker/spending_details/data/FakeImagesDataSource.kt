package com.ahmed_apps.soufian_spending_tracker.spending_details.data

import com.ahmed_apps.soufian_spending_tracker.spending_details.domian.RemoteImagesDataSource
import com.ahmed_apps.soufian_spending_tracker.spending_details.domian.model.Images

/**
 * @author Ahmed Guedmioui
 */
class FakeImagesDataSource : RemoteImagesDataSource {

    private var shouldReturnError = false
    fun setShouldReturnError(value: Boolean) {
        shouldReturnError = value
    }

    override suspend fun searchImages(
        query: String
    ): Images? {
        return if (shouldReturnError) {
            null
        } else {
            Images(
                listOf("image1", "image2", "image3", "image4")
            )
        }
    }
}