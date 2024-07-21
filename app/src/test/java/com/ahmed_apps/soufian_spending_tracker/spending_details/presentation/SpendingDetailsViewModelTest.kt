package com.ahmed_apps.soufian_spending_tracker.spending_details.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ahmed_apps.soufian_spending_tracker.MainCoroutineRule
import com.ahmed_apps.soufian_spending_tracker.spending_details.data.FakeImagesDataSource
import com.ahmed_apps.soufian_spending_tracker.core.data.FakeSpendingDataSource
import com.ahmed_apps.soufian_spending_tracker.core.domain.model.Spending
import com.ahmed_apps.soufian_spending_tracker.spending_details.domian.usecase.SearchImagesUseCase
import com.ahmed_apps.soufian_spending_tracker.spending_details.domian.usecase.UpsertSpendingUseCase
import com.google.common.truth.Truth
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import java.time.ZonedDateTime


/**
 * @author Ahmed Guedmioui
 */
class SpendingDetailsViewModelTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private val spendingDataSource = FakeSpendingDataSource()
    private val imagesDataSource = FakeImagesDataSource()
    private val upsertSpendingUseCase = UpsertSpendingUseCase(spendingDataSource)
    private val searchImagesUseCase = SearchImagesUseCase(imagesDataSource)
    private val viewModel = SpendingDetailsViewModel(
        upsertSpendingUseCase, searchImagesUseCase, spendingDataSource
    )

    private fun setSpendingInfoAndId(
        isValid: Boolean = true
    ) = runTest {
        val spending = Spending(
            id = 1,
            name = "name",
            imageUrl = "image",
            price = 10.0,
            kilograms = 10.0,
            quantity = 10.0,
            dateTimeUtc = ZonedDateTime.now()
        )
        upsertSpendingUseCase.invoke(spending)
        mainCoroutineRule.dispatcher.scheduler.advanceUntilIdle()

        viewModel.onAction(SpendingDetailsActions.SetSpendingId(1))
        mainCoroutineRule.dispatcher.scheduler.advanceUntilIdle()

        if (!isValid) {
            viewModel.onAction(SpendingDetailsActions.OnNameChange(""))
            mainCoroutineRule.dispatcher.scheduler.advanceUntilIdle()
        }
    }

    @Test
    fun spendingInfoIsSet_isSpendingInfoCorrect() = runTest {
        setSpendingInfoAndId()

        Truth.assertThat(viewModel.state.id).isEqualTo(1)
        Truth.assertThat(viewModel.state.name).isEqualTo("name")
        Truth.assertThat(viewModel.state.imageUrl).isEqualTo("image")
        Truth.assertThat(viewModel.state.price).isEqualTo(10.0)
    }

    @Test
    fun spendingInfoIsNotSet_isSpendingInfoDefault() = runTest {
        Truth.assertThat(viewModel.state.id).isEqualTo(null)
        Truth.assertThat(viewModel.state.name).isEqualTo("")
        Truth.assertThat(viewModel.state.imageUrl).isEqualTo("")
        Truth.assertThat(viewModel.state.price).isEqualTo(0.0)
    }

    @Test
    fun saveValidSpending_SpendingIsUpserted() = runTest {
        setSpendingInfoAndId()

        val isSaved = viewModel.saveSpending()
        mainCoroutineRule.dispatcher.scheduler.advanceUntilIdle()

        Truth.assertThat(isSaved).isTrue()
    }

    @Test
    fun saveInvalidValidSpending_SpendingIsNotUpserted() = runTest {
        setSpendingInfoAndId(isValid = false)

        val isSaved = viewModel.saveSpending()
        mainCoroutineRule.dispatcher.scheduler.advanceUntilIdle()

        Truth.assertThat(isSaved).isFalse()

    }

    @Test
    fun `search image with empty query, image list is empty`() = runTest {
        viewModel.searchImages("")
        mainCoroutineRule.dispatcher.scheduler.advanceUntilIdle()

        Truth.assertThat(
            viewModel.state.imageList.isEmpty()
        ).isTrue()
    }

    @Test
    fun `search image with a valid query but with error, image list is empty`() = runTest {
        imagesDataSource.setShouldReturnError(true)

        viewModel.searchImages("query")
        mainCoroutineRule.dispatcher.scheduler.advanceUntilIdle()

        Truth.assertThat(
            viewModel.state.imageList.isEmpty()
        ).isTrue()
    }

    @Test
    fun `search image with a valid query, image list is not empty`() = runTest {

        viewModel.searchImages("query")
        mainCoroutineRule.dispatcher.scheduler.advanceUntilIdle()

        Truth.assertThat(
            viewModel.state.imageList.isNotEmpty()
        ).isTrue()
    }

}


























