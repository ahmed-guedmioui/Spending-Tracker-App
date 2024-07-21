package com.ahmed_apps.soufian_spending_tracker.spending_overview.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size
import com.ahmed_apps.soufian_spending_tracker.R
import com.ahmed_apps.soufian_spending_tracker.core.domain.model.Spending
import com.ahmed_apps.soufian_spending_tracker.core.presentation.ui.theme.SoufianSpendingTrackerTheme
import com.ahmed_apps.soufian_spending_tracker.core.presentation.ui.theme.montserrat
import com.ahmed_apps.soufian_spending_tracker.core.presentation.util.GradientBackground
import com.ahmed_apps.soufian_spending_tracker.spending_overview.presentation.util.format
import com.ahmed_apps.soufian_spending_tracker.spending_overview.presentation.util.randomColor
import org.koin.androidx.compose.koinViewModel
import java.time.ZonedDateTime

/**
 * @author Ahmed Guedmioui
 */

@Composable

fun SpendingOverviewScreenCore(
    viewModel: SpendingOverviewViewModel = koinViewModel(),
    onAnalyticsClick: () -> Unit,
    onAddBalanceClick: () -> Unit,
    onSpendingClick: (Int) -> Unit,
    onAddCategoryClick: () -> Unit,
) {

    LaunchedEffect(true) {
        viewModel.onAction(SpendingOverviewActions.LoadSpendingOverviewAndBalance)
    }

    SpendingOverviewScreen(
        state = viewModel.state,
        onAction = viewModel::onAction,
        onAnalyticsClick = onAnalyticsClick,
        onAddBalanceClick = onAddBalanceClick,
        onSpendingClick = onSpendingClick,
        onAddCategoryClick = onAddCategoryClick,
        onDelete = {
            viewModel.onAction(SpendingOverviewActions.OnDeleteSpending(it))
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SpendingOverviewScreen(
    state: SpendingOverviewState,
    onAction: (SpendingOverviewActions) -> Unit,
    onAnalyticsClick: () -> Unit,
    onAddBalanceClick: () -> Unit,
    onSpendingClick: (Int) -> Unit,
    onAddCategoryClick: () -> Unit,
    onDelete: (Int) -> Unit,
) {

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(
        state = rememberTopAppBarState()
    )
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            Column {
                SpendingOverviewTopBar(
                    modifier = Modifier.fillMaxWidth(),
                    balance = state.balance,
                    scrollBehavior = scrollBehavior,
                    onAnalyticsClick = onAnalyticsClick,
                    onAddBalanceClick = onAddBalanceClick
                )

                Spacer(modifier = Modifier.height(8.dp))

                Date(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(start = 8.dp),
                    state = state,
                    onMenuItemClick = { index ->
                        onAction(SpendingOverviewActions.OnDateChange(index))
                    }
                )

                Spacer(modifier = Modifier.height(8.dp))
            }
        },
        floatingActionButton = {
            Column {
                FloatingActionButton(
                    onClick = { onAddCategoryClick() }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Add,
                        contentDescription = stringResource(R.string.add_a_new_spending)
                    )
                }
                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    ) { paddingValues ->
        GradientBackground {
            if (state.spendingList.isEmpty()) {
                Shimmer(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .alpha(0.7f)
                )
            } else {
                SpendingList(
                    state = state,
                    paddingValues = paddingValues,
                    onSpendingClick = onSpendingClick,
                    onDelete = onDelete
                )
            }
        }
    }
}

@Composable
fun Shimmer(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(
            top = 16.dp, bottom = 80.dp
        ),
    ) {
        repeat(10) {
            item {
                ShimmerItem(
                    modifier = Modifier
                        .height(150.dp)
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }

    }
}

@Composable
fun ShimmerItem(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(22.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(150.dp)
                .padding(10.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(
                    MaterialTheme.colorScheme.onSurfaceVariant.copy(0.2f)
                )
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(vertical = 12.dp),
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .height(16.dp)
                    .background(
                        MaterialTheme.colorScheme.onSurfaceVariant.copy(0.2f)
                    )
            )

            Column {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .height(12.dp)
                        .background(
                            MaterialTheme.colorScheme.onSurfaceVariant.copy(0.2f)
                        )
                )
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.4f)
                        .height(12.dp)
                        .background(
                            MaterialTheme.colorScheme.onSurfaceVariant.copy(0.2f)
                        )
                )
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.3f)
                        .height(12.dp)
                        .background(
                            MaterialTheme.colorScheme.onSurfaceVariant.copy(0.2f)
                        )
                )
            }

        }
    }
}

@Composable
fun SpendingList(
    state: SpendingOverviewState,
    paddingValues: PaddingValues,
    onSpendingClick: (Int) -> Unit,
    onDelete: (Int) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        contentPadding = PaddingValues(
            top = 16.dp, bottom = 80.dp
        ),
    ) {
        itemsIndexed(state.spendingList) { index, category ->
            SpendingItem(
                modifier = Modifier
                    .height(150.dp)
                    .padding(horizontal = 16.dp),
                spending = category,
                onSpendingClick = onSpendingClick,
                onDelete = onDelete
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SpendingItem(
    spending: Spending,
    onSpendingClick: (Int) -> Unit,
    onDelete: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var showDropDown by remember {
        mutableStateOf(false)
    }

    Box {
        ElevatedCard(
            modifier = modifier,
            shape = RoundedCornerShape(22.dp),
            elevation = CardDefaults.elevatedCardElevation(
                defaultElevation = 4.dp
            ),
        ) {
            Row(
                modifier = Modifier
                    .background(Color(spending.color))
                    .combinedClickable(
                        onClick = { onSpendingClick(spending.id ?: -1) },
                        onLongClick = { showDropDown = true }
                    )
                    .padding(8.dp)
            ) {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(130.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(spending.imageUrl)
                        .size(Size.ORIGINAL)
                        .build(),
                    contentDescription = spending.name,
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.width(16.dp))

                Column(
                    modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.SpaceAround
                ) {

                    Text(
                        text = spending.name,
                        overflow = TextOverflow.Ellipsis,
                        fontWeight = FontWeight.Medium,
                        maxLines = 1,
                        fontSize = 21.sp,
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    Spacer(modifier = Modifier.height(1.dp))

                    SpendingItemInfo(
                        name = stringResource(R.string.price),
                        value = "$${spending.price}"
                    )

                    SpendingItemInfo(
                        name = stringResource(R.string.kilograms),
                        value = "${spending.kilograms}"
                    )

                    SpendingItemInfo(
                        name = stringResource(R.string.quantity),
                        value = "${spending.quantity}"
                    )
                }
            }
        }

        DropdownMenu(
            expanded = showDropDown,
            onDismissRequest = { showDropDown = false },
            offset = DpOffset(30.dp, 0.dp)
        ) {
            DropdownMenuItem(
                text = {
                    Text(
                        text = stringResource(R.string.delete_spending),
                        fontFamily = montserrat
                    )
                },
                onClick = {
                    showDropDown = false
                    onDelete(spending.id ?: -1)
                }
            )
        }
    }
}

@Composable
fun SpendingItemInfo(
    modifier: Modifier = Modifier,
    name: String,
    value: String
) {
    Row(modifier.fillMaxWidth()) {
        Text(
            text = "$name: ",
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.Normal,
            maxLines = 1,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onBackground.copy(0.8f)
        )

        Text(
            text = value,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.Normal,
            maxLines = 1,
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
fun Date(
    modifier: Modifier = Modifier,
    state: SpendingOverviewState,
    onMenuItemClick: (Int) -> Unit
) {

    var isDropDownOpen by rememberSaveable {
        mutableStateOf(false)
    }

    Box(
        modifier = modifier
            .shadow(
                elevation = 0.5.dp,
                shape = RoundedCornerShape(16.dp)
            )
    ) {
        DropdownMenu(
            modifier = Modifier.height(440.dp),
            offset = DpOffset(10.dp, 0.dp),
            expanded = isDropDownOpen,
            onDismissRequest = {
                isDropDownOpen = false
            }
        ) {
            state.allDates.forEachIndexed { index, date ->
                Column {
                    if (index == 0) {
                        HorizontalDivider(modifier = Modifier.height(1.dp))
                    }
                    Row(
                        modifier = Modifier
                            .clickable {
                                isDropDownOpen = false
                                onMenuItemClick(index)
                            }
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(
                            text = date.format(),
                            fontWeight = FontWeight.Normal,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                    HorizontalDivider(modifier = Modifier.height(1.dp))
                }
            }
        }

        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .clickable { isDropDownOpen = true }
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Text(
                text = state.pickedDate.format(),
                fontSize = 20.sp,
                fontWeight = FontWeight.Normal
            )

            Spacer(modifier = Modifier.width(8.dp))

            Icon(
                imageVector = Icons.Rounded.KeyboardArrowDown,
                contentDescription = stringResource(R.string.pick_a_new_date)
            )
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpendingOverviewTopBar(
    modifier: Modifier = Modifier,
    balance: Double,
    scrollBehavior: TopAppBarScrollBehavior,
    onAnalyticsClick: () -> Unit,
    onAddBalanceClick: () -> Unit
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
            scrolledContainerColor = Color.Transparent
        ),
        scrollBehavior = scrollBehavior,
        modifier = modifier.padding(end = 16.dp, start = 12.dp),
        title = {
            Text(
                text = "$$balance",
                fontSize = 35.sp,
                color = MaterialTheme.colorScheme.primary,
                maxLines = 1,
                fontFamily = montserrat,
                overflow = TextOverflow.Ellipsis
            )
        },
        actions = {

            Spacer(modifier = Modifier.width(8.dp))

            Box(
                modifier = Modifier
                    .size(45.dp)
                    .clip(RoundedCornerShape(13.dp))
                    .border(
                        width = 2.dp,
                        color = MaterialTheme.colorScheme.primary.copy(0.6f),
                        shape = RoundedCornerShape(13.dp)
                    )
                    .background(
                        MaterialTheme.colorScheme.primaryContainer.copy(0.3f)
                    )
                    .clickable { onAddBalanceClick() },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "$",
                    fontSize = 26.sp,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.SemiBold,
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Box(
                modifier = Modifier
                    .size(45.dp)
                    .clip(RoundedCornerShape(13.dp))
                    .border(
                        width = 2.dp,
                        color = MaterialTheme.colorScheme.primary.copy(0.6f),
                        shape = RoundedCornerShape(13.dp)
                    )
                    .background(
                        MaterialTheme.colorScheme.primaryContainer.copy(0.3f)
                    )
                    .clickable { onAnalyticsClick() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    modifier = Modifier.size(30.dp),
                    painter = painterResource(id = R.drawable.graph),
                    contentDescription = stringResource(R.string.analytics),
                    tint = MaterialTheme.colorScheme.primary,
                )
            }

        }
    )
}

@Preview
@Composable
private fun SpendingOverviewScreenPreview() {
    SoufianSpendingTrackerTheme {

        val spendingList = mutableListOf<Spending>()
        val dates = mutableListOf<ZonedDateTime>()
        for (i in 1..10) {
            val spending = Spending(
                i,
                "Category $i",
                "https://deep-image.ai/_next/static/media/app-info-generator.bf08e63e.webp",
                i.toDouble(),
                i.toDouble(),
                i.toDouble(),
                ZonedDateTime.now(),
                randomColor().toArgb(),
            )
            spendingList.add(spending)
            dates.add(ZonedDateTime.now())
        }

        SpendingOverviewScreen(
            state = SpendingOverviewState(
                spendingList = spendingList,
                balance = 240.24,
                pickedDate = ZonedDateTime.now(),
                allDates = dates
            ),
            onAction = {},
            onAnalyticsClick = {},
            onAddBalanceClick = {},
            onSpendingClick = {},
            onAddCategoryClick = {},
            onDelete = {}
        )
    }
}















