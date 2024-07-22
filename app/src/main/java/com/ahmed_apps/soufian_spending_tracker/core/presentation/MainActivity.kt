package com.ahmed_apps.soufian_spending_tracker.core.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.ahmed_apps.soufian_spending_tracker.analytics.presentation.AnalyticsScreenCore
import com.ahmed_apps.soufian_spending_tracker.balance.presentation.BalanceScreenCore
import com.ahmed_apps.soufian_spending_tracker.spending_overview.presentation.SpendingOverviewScreenCore
import com.ahmed_apps.soufian_spending_tracker.spending_details.presentation.SpendingDetailsScreenCore
import com.ahmed_apps.soufian_spending_tracker.core.presentation.ui.theme.SoufianSpendingTrackerTheme
import com.ahmed_apps.soufian_spending_tracker.core.presentation.util.Screen
import com.ahmed_apps.soufian_spending_tracker.core.presentation.util.ads.InterManager

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        InterManager.loadInterstitial(this)
        setContent {
            SoufianSpendingTrackerTheme {
                Navigation(modifier = Modifier.fillMaxSize())
            }
        }
    }

    @Composable
    fun Navigation(modifier: Modifier = Modifier) {
        val navController = rememberNavController()

        NavHost(
            modifier = modifier,
            navController = navController,
            startDestination = Screen.SpendingOverview
        ) {

            composable<Screen.SpendingOverview> {
                SpendingOverviewScreenCore(
                    onAnalyticsClick = {
                        navController.navigate(Screen.Analytics)
                    },
                    onAddBalanceClick = {
                        navController.navigate(Screen.Balance)
                    },
                    onSpendingClick = { categoryId ->
                        InterManager.showInterstitial(this@MainActivity) {
                            navController.navigate(Screen.SpendingDetails(categoryId))
                        }
                    },
                    onAddCategoryClick = {
                        navController.navigate(Screen.SpendingDetails())
                    }
                )
            }

            composable<Screen.SpendingDetails> { backStackEntry ->
                val spendingId: Screen.SpendingDetails = backStackEntry.toRoute()
                SpendingDetailsScreenCore(
                    id = spendingId.spendingId,
                    onSpendingSaved = {
                        InterManager.showInterstitial(this@MainActivity) {
                            navController.popBackStack()
                            navController.navigate(Screen.SpendingOverview)
                        }
                    }
                )
            }

            composable<Screen.Balance> {
                BalanceScreenCore(
                    onSaveClick = {
                        InterManager.showInterstitial(this@MainActivity) {
                            navController.popBackStack()
                        }
                    }
                )
            }

            composable<Screen.Analytics> {
                AnalyticsScreenCore()
            }
        }
    }
}















