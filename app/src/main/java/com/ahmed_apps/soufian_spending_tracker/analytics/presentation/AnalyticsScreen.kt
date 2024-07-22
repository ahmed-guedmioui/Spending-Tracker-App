package com.ahmed_apps.soufian_spending_tracker.analytics.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ahmed_apps.soufian_spending_tracker.R
import com.ahmed_apps.soufian_spending_tracker.analytics.presentation.components.SpendingGraph
import com.ahmed_apps.soufian_spending_tracker.core.presentation.ui.theme.montserrat
import com.ahmed_apps.soufian_spending_tracker.core.presentation.util.GradientBackground
import org.koin.androidx.compose.koinViewModel

/**
 * @author Ahmed Guedmioui
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun AnalyticsScreenCore(
    viewModel: AnalyticsViewModel = koinViewModel()
) {

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier,
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    scrolledContainerColor = Color.Transparent
                ),
                title = {
                    Text(
                        modifier = Modifier.padding(start = 8.dp),
                        text = stringResource(R.string.analytics),
                        fontFamily = montserrat,
                        fontSize = 23.sp,
                        color = MaterialTheme.colorScheme.primary,
                    )
                }
            )
        }
    ) { paddingValues ->
        GradientBackground {
            AnalyticsScreen(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                state = viewModel.state
            )
        }
    }
}

@Composable
private fun AnalyticsScreen(
    modifier: Modifier = Modifier,
    state: AnalyticsState
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
    ) {

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            modifier = Modifier
                .padding(horizontal = 22.dp),
            text = stringResource(R.string.you_spent_in_total, state.usedBalance),
            fontWeight = FontWeight.Normal,
            fontSize = 22.sp,
        )

        Spacer(modifier = Modifier.height(8.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant.copy(0.6f))
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                modifier = Modifier.padding(horizontal = 20.dp),
                text = stringResource(R.string.spending_per_day),
                fontWeight = FontWeight.Normal,
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.primary,
            )

            if (state.pricesPerDay.isNotEmpty() && state.datesPerDay.isNotEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .padding(16.dp)
                        .padding(top = 16.dp, bottom = 6.dp),
                    contentAlignment = Alignment.TopCenter
                ) {
                    SpendingGraph(
                        modifier = Modifier
                            .padding(start = 15.dp),
                        prices = state.pricesPerDay,
                        dates = state.datesPerDay
                    )
                }
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(R.string.no_data_yet),
                        fontWeight = FontWeight.Normal,
                        fontSize = 20.sp,
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))
        HorizontalDivider(modifier = Modifier.padding(horizontal = 20.dp))
        Spacer(modifier = Modifier.height(10.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant.copy(0.6f))
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                modifier = Modifier.padding(horizontal = 20.dp),
                text = stringResource(R.string.spending_per_week),
                fontWeight = FontWeight.Normal,
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.primary,
            )

            if (state.pricesPerWeek.isNotEmpty() && state.datesPerWeek.isNotEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .padding(16.dp)
                        .padding(top = 16.dp, bottom = 6.dp),
                    contentAlignment = Alignment.TopCenter
                ) {
                    SpendingGraph(
                        modifier = Modifier
                            .padding(start = 15.dp),
                        prices = state.pricesPerWeek,
                        dates = state.datesPerWeek
                    )
                }
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(R.string.no_data_yet),
                        fontWeight = FontWeight.Normal,
                        fontSize = 20.sp,
                    )
                }
            }
        }
    }
}










