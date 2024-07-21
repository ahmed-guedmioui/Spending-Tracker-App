package com.ahmed_apps.soufian_spending_tracker.spending_details.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmed_apps.soufian_spending_tracker.core.domain.LocalSpendingDataSource
import com.ahmed_apps.soufian_spending_tracker.core.domain.model.Spending
import com.ahmed_apps.soufian_spending_tracker.spending_details.domian.usecase.SearchImagesUseCase
import com.ahmed_apps.soufian_spending_tracker.spending_details.domian.usecase.UpsertSpendingUseCase
import com.ahmed_apps.soufian_spending_tracker.spending_details.domian.util.Resource
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.ZonedDateTime

/**
 * @author Ahmed Guedmioui
 */
class SpendingDetailsViewModel(
    private val upsertSpendingUseCase: UpsertSpendingUseCase,
    private val searchImagesUseCase: SearchImagesUseCase,
    private val spendingDataSource: LocalSpendingDataSource,
) : ViewModel() {

    var state by mutableStateOf(SpendingDetailsState())
        private set

    private val _eventChannel = Channel<SpendingDetailsEvent>()
    val eventFlow = _eventChannel.receiveAsFlow()

    fun onAction(action: SpendingDetailsActions) {
        when (action) {
            is SpendingDetailsActions.SetSpendingId -> {
                if (action.categoryId != -1) {
                    viewModelScope.launch {
                        val spending = spendingDataSource.getSpending(action.categoryId)
                        spending?.let {
                            state = state.copy(
                                id = spending.id,
                                name = spending.name,
                                price = spending.price,
                                imageUrl = spending.imageUrl
                            )
                        }
                    }
                }
            }

            is SpendingDetailsActions.OnNameChange -> {
                state = state.copy(
                    name = action.newName
                )
            }

            is SpendingDetailsActions.OnBalanceChange -> {
                state = state.copy(
                    price = action.newBalance
                )
            }


            is SpendingDetailsActions.OnKilogramsChange -> {
                state = state.copy(
                    kilograms = action.newKilograms
                )
            }
            is SpendingDetailsActions.OnQuantityChange -> {
                state = state.copy(
                    quantity = action.newQuantity
                )
            }
            is SpendingDetailsActions.OnImageUrlChange -> {
                state = state.copy(
                    imageUrl = action.newImageUrl
                )
            }

            SpendingDetailsActions.SaveSpending -> {
                viewModelScope.launch {
                    if (saveSpending()) {
                        _eventChannel.send(SpendingDetailsEvent.SaveSuccess)
                    } else {
                        _eventChannel.send(SpendingDetailsEvent.SaveFailure)
                    }
                }
            }

            SpendingDetailsActions.UpdateImagePickerDialogVisibility -> {
                state = state.copy(
                    isImagePickerDialogVisible = !state.isImagePickerDialogVisible
                )
            }

            is SpendingDetailsActions.UpdateSearchImageQuery -> {
                state = state.copy(
                    searchImagesQuery = action.newQuery
                )
                searchImages(action.newQuery)
            }

            is SpendingDetailsActions.PickImage -> {
                state = state.copy(
                    imageUrl = action.imageUrl
                )
            }
        }
    }

    private var searchJob: Job? = null

    fun searchImages(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500)

            searchImagesUseCase
                .invoke(query)
                .collect { result ->
                    state = when (result) {
                        is Resource.Error -> {
                            state.copy(
                                imageList = emptyList()
                            )
                        }

                        is Resource.Success -> {
                            state.copy(
                                imageList = result.data?.images ?: emptyList()
                            )
                        }
                    }
                }
        }
    }

    suspend fun saveSpending(): Boolean {
        val spending = Spending(
            id = state.id,
            name = state.name,
            price = state.price,
            kilograms = state.kilograms,
            quantity = state.quantity,
            imageUrl = state.imageUrl,
            dateTimeUtc = ZonedDateTime.now()
        )
        return upsertSpendingUseCase.invoke(spending)
    }

}



















