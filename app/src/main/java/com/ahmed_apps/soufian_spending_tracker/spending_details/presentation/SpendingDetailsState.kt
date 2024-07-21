package com.ahmed_apps.soufian_spending_tracker.spending_details.presentation

/**
 * @author Ahmed Guedmioui
 */
data class SpendingDetailsState(
    val id: Int? = null,
    val name: String = "",
    val price: Double = 0.0,
    val imageUrl: String = "",
    val kilograms: Double = 0.0,
    val quantity: Double = 0.0,

    val isImagePickerDialogVisible: Boolean = false,
    val imageList: List<String> = emptyList(),
    val searchImagesQuery: String = "",
    val isLoadingImages: Boolean = false
)
