package com.ahmed_apps.soufian_spending_tracker.spending_details.presentation

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size
import com.ahmed_apps.soufian_spending_tracker.R
import com.ahmed_apps.soufian_spending_tracker.core.presentation.ui.theme.SoufianSpendingTrackerTheme
import com.ahmed_apps.soufian_spending_tracker.core.presentation.ui.theme.montserrat
import com.ahmed_apps.soufian_spending_tracker.core.presentation.util.GradientBackground
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

/**
 * @author Ahmed Guedmioui
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun SpendingDetailsScreenCore(
    viewModel: SpendingDetailsViewModel = koinViewModel(),
    id: Int,
    onSpendingSaved: () -> Unit,
) {

    if (id != -1) {
        viewModel.onAction(SpendingDetailsActions.SetSpendingId(id))
    }

    val context = LocalContext.current
    LaunchedEffect(true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                SpendingDetailsEvent.SaveSuccess -> onSpendingSaved()
                SpendingDetailsEvent.SaveFailure -> {
                    Toast.makeText(
                        context,
                        R.string.error_saving_spending,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.padding(horizontal = 16.dp),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    scrolledContainerColor = Color.Transparent
                ),
                title = {
                    Text(
                        text = if (id == -1) {
                            stringResource(R.string.add_a_new_spending)
                        } else {
                            stringResource(R.string.edite_spending)
                        },
                        fontFamily = montserrat,
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.primary,
                    )
                },
                actions = {
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
                            .clickable {
                                viewModel.onAction(
                                    SpendingDetailsActions.SaveSpending
                                )
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            modifier = Modifier.size(30.dp),
                            imageVector = Icons.Outlined.Check,
                            contentDescription = stringResource(R.string.analytics),
                            tint = MaterialTheme.colorScheme.primary,
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        GradientBackground {
            SpendingDetailsScreen(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                state = viewModel.state,
                onAction = viewModel::onAction
            )
        }
    }
}

@Composable
private fun SpendingDetailsScreen(
    modifier: Modifier = Modifier,
    state: SpendingDetailsState,
    onAction: (SpendingDetailsActions) -> Unit
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .padding(horizontal = 16.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(
                    MaterialTheme.colorScheme.primaryContainer.copy(0.3f)
                ),
            contentAlignment = Alignment.Center
        ) {
            if (state.imageUrl.isEmpty()) {
                Text(
                    text = stringResource(R.string.add_an_image),
                    fontWeight = FontWeight.Normal
                )
            }

            AsyncImage(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable {
                        onAction(
                            SpendingDetailsActions.UpdateImagePickerDialogVisibility
                        )
                    },
                model = ImageRequest
                    .Builder(LocalContext.current)
                    .data(state.imageUrl)
                    .size(Size.ORIGINAL)
                    .build(),
                contentDescription = state.searchImagesQuery,
                contentScale = ContentScale.Crop
            )
        }


        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            value = state.name,
            onValueChange = {
                onAction(
                    SpendingDetailsActions.OnNameChange(it)
                )
            },
            label = {
                Text(
                    text = stringResource(R.string.name),
                    fontWeight = FontWeight.Medium
                )
            },
            textStyle = TextStyle(
                fontFamily = montserrat,
                fontSize = 17.sp
            ),
            maxLines = 1,
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = MaterialTheme.colorScheme.primary
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            value = state.price.toString(),
            onValueChange = {
                onAction(
                    SpendingDetailsActions.OnBalanceChange(
                        it.toDoubleOrNull() ?: 0.0
                    )
                )
            },
            label = {
                Text(
                    text = stringResource(R.string.price),
                    fontWeight = FontWeight.Medium
                )
            },
            textStyle = TextStyle(
                fontFamily = montserrat,
                fontSize = 18.sp
            ),
            maxLines = 1,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal
            ),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = MaterialTheme.colorScheme.primary
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            OutlinedTextField(
                modifier = Modifier.weight(1f),
                value = state.kilograms.toString(),
                onValueChange = {
                    onAction(
                        SpendingDetailsActions.OnKilogramsChange(
                            it.toDoubleOrNull() ?: 0.0
                        )
                    )
                },
                label = {
                    Text(
                        text = stringResource(R.string.kilograms),
                        fontWeight = FontWeight.Medium
                    )
                },
                textStyle = TextStyle(
                    fontFamily = montserrat,
                    fontSize = 18.sp
                ),
                maxLines = 1,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal
                ),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = MaterialTheme.colorScheme.primary
                )
            )

            Spacer(modifier = Modifier.width(12.dp))

            OutlinedTextField(
                modifier = Modifier.weight(1f),
                value = state.quantity.toString(),
                onValueChange = {
                    onAction(
                        SpendingDetailsActions.OnQuantityChange(
                            it.toDoubleOrNull() ?: 0.0
                        )
                    )
                },
                label = {
                    Text(
                        text = stringResource(R.string.quantity),
                        fontWeight = FontWeight.Medium
                    )
                },
                textStyle = TextStyle(
                    fontFamily = montserrat,
                    fontSize = 18.sp
                ),
                maxLines = 1,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal
                ),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    }

    if (state.isImagePickerDialogVisible) {
        Dialog(
            onDismissRequest = {
                onAction(
                    SpendingDetailsActions.UpdateImagePickerDialogVisibility
                )
            }
        ) {
            ImagesDialogContent(
                state = state,
                onSearchQueryChange = {
                    onAction(
                        SpendingDetailsActions.UpdateSearchImageQuery(it)
                    )
                },
                onImageClick = {
                    onAction(
                        SpendingDetailsActions.PickImage(it)
                    )
                },
                onSetImageClick = {
                    onAction(
                        SpendingDetailsActions.UpdateImagePickerDialogVisibility
                    )
                }
            )
        }
    }
}

@Composable
fun ImagesDialogContent(
    state: SpendingDetailsState,
    onSearchQueryChange: (String) -> Unit,
    onImageClick: (String) -> Unit,
    onSetImageClick: () -> Unit,
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.6f)
            .clip(RoundedCornerShape(26.dp))
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(22.dp))

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            value = state.searchImagesQuery,
            onValueChange = {
                onSearchQueryChange(it)
            },
            label = {
                Text(
                    text = stringResource(R.string.search_image),
                    fontWeight = FontWeight.Light
                )
            },
            textStyle = TextStyle(
                fontFamily = montserrat,
                fontSize = 17.sp
            ),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = MaterialTheme.colorScheme.primary
            ),
            trailingIcon = {
                if (state.searchImagesQuery.isNotEmpty()) {
                    Icon(
                        modifier = Modifier.clickable { onSetImageClick() },
                        imageVector = Icons.Rounded.Check,
                        contentDescription = stringResource(R.string.set_image),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        if (state.isLoadingImages) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            LazyVerticalGrid(
                modifier = Modifier.fillMaxSize(),
                columns = GridCells.Adaptive(120.dp),
                contentPadding = PaddingValues(horizontal = 8.dp)
            ) {

                itemsIndexed(state.imageList) { _, url ->
                    AsyncImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp)
                            .padding(8.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(MaterialTheme.colorScheme.primaryContainer)
                            .clickable { onImageClick(url) },
                        model = ImageRequest
                            .Builder(LocalContext.current)
                            .data(url)
                            .size(Size.ORIGINAL)
                            .build(),
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )
                }

            }
        }
    }
}

@Preview
@Composable
private fun SpendingDetailsScreenPreview() {
    SoufianSpendingTrackerTheme {
        SpendingDetailsScreen(
            state = SpendingDetailsState(),
            onAction = {}
        )
    }
}