package com.ahmed_apps.soufian_spending_tracker.spending_details.domian.usecase

import com.ahmed_apps.soufian_spending_tracker.spending_details.domian.RemoteImagesDataSource
import com.ahmed_apps.soufian_spending_tracker.spending_details.domian.model.Images
import com.ahmed_apps.soufian_spending_tracker.spending_details.domian.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * @author Ahmed Guedmioui
 */
class SearchImagesUseCase(
    private val imagesDataSource: RemoteImagesDataSource
) {

    suspend operator fun invoke(query: String): Flow<Resource<Images>> {
        return flow {

            if (query.isEmpty()) {
                emit(Resource.Error())
                return@flow
            }

            val images = try {
                imagesDataSource.searchImages(query)
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Resource.Error())
                return@flow
            }

            images?.let {
                emit(Resource.Success(it))
                return@flow
            }

            emit(Resource.Error())
        }
    }

}












