package com.ahmed_apps.soufian_spending_tracker.balance.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ahmed_apps.soufian_spending_tracker.R
import com.ahmed_apps.soufian_spending_tracker.core.presentation.ui.theme.SoufianSpendingTrackerTheme
import com.ahmed_apps.soufian_spending_tracker.core.presentation.ui.theme.montserrat
import com.ahmed_apps.soufian_spending_tracker.core.presentation.util.GradientBackground
import org.koin.androidx.compose.koinViewModel

/**
 * @author Ahmed Guedmioui
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun BalanceScreenCore(
    viewModel: BalanceViewModel = koinViewModel(),
    onSaveClick: () -> Unit
) {

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .padding(start = 4.dp),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    scrolledContainerColor = Color.Transparent
                ),
                title = {
                    Text(
                        modifier = Modifier.padding(start = 8.dp),
                        text = stringResource(R.string.update_balance),
                        fontFamily = montserrat,
                        fontSize = 23.sp,
                        color = MaterialTheme.colorScheme.primary,
                    )
                }
            )
        }
    ) { paddingValues ->
        GradientBackground {
            BalanceScreen(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                state = viewModel.state,
                onAction = viewModel::onAction,
                onSaveClick = {
                    viewModel.onAction(BalanceActions.OnSaveBalance)
                    onSaveClick()
                }
            )
        }
    }
}

@Composable
private fun BalanceScreen(
    modifier: Modifier = Modifier,
    state: BalanceState,
    onAction: (BalanceActions) -> Unit,
    onSaveClick: () -> Unit
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "$${state.balance}",
            fontWeight = FontWeight.SemiBold,
            fontSize = 40.sp,
            color = MaterialTheme.colorScheme.primary,
        )

        Spacer(modifier = Modifier.height(36.dp))

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            value = state.balance.toString(),
            onValueChange = {
                onAction(
                    BalanceActions.OnBalanceChange(
                        it.toDoubleOrNull() ?: 0.0
                    )
                )
            },
            label = {
                Text(
                    text = stringResource(R.string.enter_balance),
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

        Spacer(modifier = Modifier.height(30.dp))

        OutlinedButton(
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 16.dp),
            onClick = {
                onSaveClick()
            }
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(35.dp),
                    imageVector = Icons.Rounded.Check,
                    contentDescription = stringResource(R.string.save_balance)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = stringResource(R.string.save_balance),
                    fontFamily = montserrat,
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.primary,
                )
            }

        }
    }

}


@Preview
@Composable
private fun BalanceScreenPreview() {
    SoufianSpendingTrackerTheme {
        BalanceScreen(
            state = BalanceState(13.00),
            onAction = {},
            onSaveClick = {}
        )
    }
}