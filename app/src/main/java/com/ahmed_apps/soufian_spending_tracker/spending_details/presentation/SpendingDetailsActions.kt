package com.ahmed_apps.soufian_spending_tracker.spending_details.presentation

/**
 * @author Ahmed Guedmioui
 */
sealed interface SpendingDetailsActions {
    data class SetSpendingId(val categoryId: Int) : SpendingDetailsActions
    data class OnNameChange(val newName: String) : SpendingDetailsActions
    data class OnBalanceChange(val newBalance: Double) : SpendingDetailsActions
    data class OnKilogramsChange(val newKilograms: Double) : SpendingDetailsActions
    data class OnQuantityChange(val newQuantity: Double) : SpendingDetailsActions
    data class OnImageUrlChange(val newImageUrl: String) : SpendingDetailsActions
    data object SaveSpending: SpendingDetailsActions

    data object UpdateImagePickerDialogVisibility: SpendingDetailsActions
    data class UpdateSearchImageQuery(val newQuery: String) : SpendingDetailsActions
    data class PickImage(val imageUrl: String) : SpendingDetailsActions
}
