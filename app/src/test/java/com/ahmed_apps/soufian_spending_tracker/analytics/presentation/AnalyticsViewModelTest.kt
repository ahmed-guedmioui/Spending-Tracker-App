package com.ahmed_apps.soufian_spending_tracker.analytics.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ahmed_apps.soufian_spending_tracker.MainCoroutineRule
import com.ahmed_apps.soufian_spending_tracker.analytics.data.AnalyticsDataSourceImpl
import com.ahmed_apps.soufian_spending_tracker.analytics.domain.model.DailySpending
import com.ahmed_apps.soufian_spending_tracker.core.data.FakeCoreDataSourceImpl
import com.ahmed_apps.soufian_spending_tracker.core.data.FakeSpendingDataSource
import com.google.common.truth.Truth
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test


/**
 * @author Ahmed Guedmioui
 */
class AnalyticsViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private val spendingDataSource = FakeSpendingDataSource()
    private val analyticsDataSource = AnalyticsDataSourceImpl(spendingDataSource)
    private val coreDataSource = FakeCoreDataSourceImpl()
    private val viewModel = AnalyticsViewModel(analyticsDataSource, coreDataSource)

    @Test
    fun `get graph items, graph items are correct`() = runTest {
        viewModel.getAnalyticsData()
        mainCoroutineRule.dispatcher.scheduler.advanceUntilIdle()

        val dailySpendings = listOf(
            DailySpending(price = 3.0, date = "2023-7-18"),
            DailySpending(price = 7.0, date = "2023-7-19"),
            DailySpending(price = 0.0, date = "2023-7-20"),
            DailySpending(price = 11.0, date = "2023-7-21")
        )

        Truth.assertThat(viewModel.state.graphItems[0].price).isEqualTo(dailySpendings[0].price)
        Truth.assertThat(viewModel.state.graphItems[1].price).isEqualTo(dailySpendings[1].price)
        Truth.assertThat(viewModel.state.graphItems[2].price).isEqualTo(dailySpendings[2].price)
        Truth.assertThat(viewModel.state.graphItems[3].price).isEqualTo(dailySpendings[3].price)

        Truth.assertThat(viewModel.state.graphItems[0].date).isEqualTo(dailySpendings[0].date)
        Truth.assertThat(viewModel.state.graphItems[1].date).isEqualTo(dailySpendings[1].date)
        Truth.assertThat(viewModel.state.graphItems[2].date).isEqualTo(dailySpendings[2].date)
        Truth.assertThat(viewModel.state.graphItems[3].date).isEqualTo(dailySpendings[3].date)
    }

}



















