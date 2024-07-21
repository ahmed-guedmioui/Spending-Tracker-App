package com.ahmed_apps.soufian_spending_tracker.spending_details.data.mappers

import com.ahmed_apps.soufian_spending_tracker.spending_details.data.remote.dto.ImageListDto
import com.ahmed_apps.soufian_spending_tracker.spending_details.domian.model.Images

/**
 * @author Ahmed Guedmioui
 */
fun ImageListDto.toImages(): Images {
    return Images(
        images = hits?.map { imageDto ->
            imageDto.previewURL ?: ""
        } ?: emptyList()
    )
}