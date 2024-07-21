package com.ahmed_apps.soufian_spending_tracker.spending_overview.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ahmed_apps.soufian_spending_tracker.MainCoroutineRule
import com.ahmed_apps.soufian_spending_tracker.analytics.data.AnalyticsDataSourceImpl
import com.ahmed_apps.soufian_spending_tracker.analytics.presentation.AnalyticsViewModel
import com.ahmed_apps.soufian_spending_tracker.core.data.FakeSpendingDataSource
import com.ahmed_apps.soufian_spending_tracker.core.data.FakeCoreDataSourceImpl
import com.google.common.truth.Truth
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test


/**
 * @author Ahmed Guedmioui
 */
class SpendingOverviewViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private val spendingDataSource = FakeSpendingDataSource()
    private val coreDataSource = FakeCoreDataSourceImpl()
    private val viewModel = SpendingOverviewViewModel(spendingDataSource, coreDataSource)

    @Before
    fun setUp() = runTest {
        viewModel.onAction(SpendingOverviewActions.LoadSpendingOverviewAndBalance)
    }


    @Test
    fun changeDate_areCategoriesFromPickedDate() = runTest {
        mainCoroutineRule.dispatcher.scheduler.advanceUntilIdle()

        println("last: " + viewModel.state.spendingList.last().name)
        Truth.assertThat(
            viewModel.state.spendingList.first().name
        ).isEqualTo("Category 6")

        viewModel.onAction(SpendingOverviewActions.OnDateChange(1))

        mainCoroutineRule.dispatcher.scheduler.advanceUntilIdle()

        println("new last: " + viewModel.state.spendingList.last().name)
        Truth.assertThat(
            viewModel.state.spendingList.first().name
        ).isEqualTo("Category 4")
    }

    @Test
    fun deleteCategory_CategoryIsDeleted() = runTest {
        mainCoroutineRule.dispatcher.scheduler.advanceUntilIdle()

        println("size: " + viewModel.state.spendingList.size)
        val category = viewModel.state.spendingList[0].copy()

        Truth.assertThat(
            viewModel.state.spendingList
        ).contains(category)

        viewModel.onAction(SpendingOverviewActions.OnDeleteSpending(category.id!!))

        mainCoroutineRule.dispatcher.scheduler.advanceUntilIdle()

        println("size: " + viewModel.state.spendingList.size)
        Truth.assertThat(
            viewModel.state.spendingList
        ).doesNotContain(category)
    }
}



















