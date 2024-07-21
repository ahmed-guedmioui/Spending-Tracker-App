package com.ahmed_apps.soufian_spending_tracker.spending_details.domian.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ahmed_apps.soufian_spending_tracker.MainCoroutineRule
import com.ahmed_apps.soufian_spending_tracker.core.data.FakeSpendingDataSource
import com.ahmed_apps.soufian_spending_tracker.core.domain.model.Spending
import com.google.common.truth.Truth
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import java.time.ZonedDateTime


/**
 * @author Ahmed Guedmioui
 */
class UpsertSpendingUseCaseTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private val spendingDataSource = FakeSpendingDataSource()
    private val upsertSpendingUseCase = UpsertSpendingUseCase(spendingDataSource)

    @Test
    fun upsertValidCategory_categoryIsUpserted() = runTest {
        val spending = Spending(
            id = 1,
            name = "Category",
            imageUrl = "image",
            price = 10.0,
            kilograms = 10.0,
            quantity = 10.0,
            dateTimeUtc = ZonedDateTime.now()
        )
        val isCategoryUpserted = upsertSpendingUseCase.invoke(spending)

        Truth.assertThat(isCategoryUpserted).isTrue()

        Truth.assertThat(
            spendingDataSource.getSpending(1)
        ).isNotNull()

        Truth.assertThat(
            spendingDataSource.getSpending(1)
        ).isEqualTo(spending)
    }

    @Test
    fun upsertInvalidCategory_categoryIsNotUpserted() = runTest {
        val spending = Spending(
            id = 1,
            name = "",
            imageUrl = "image",
            price = 10.0,
            kilograms = 10.0,
            quantity = 10.0,
            dateTimeUtc = ZonedDateTime.now()
        )

        val isCategoryUpserted = upsertSpendingUseCase.invoke(spending)

        Truth.assertThat(isCategoryUpserted).isFalse()

        Truth.assertThat(
            spendingDataSource.getSpending(1)
        ).isNull()
    }
}

















