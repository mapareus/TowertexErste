package com.towertex.erste.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.towertex.erste.compose.AccountDetail
import com.towertex.erste.compose.AccountList
import com.towertex.erste.compose.TransactionList

@Composable
fun ErsteNavHost() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(
            route = Screen.Home.route,
        ) {
            AccountList(
                onClick = { navController.navigate(Screen.AccountDetail.createRoute(accountNumber = it)) }
            )
        }
        composable(
            route = Screen.AccountDetail.route,
            arguments = Screen.AccountDetail.navArguments
        ) { stack ->
            AccountDetail(
                accountNumber = stack.arguments?.getString(Screen.ACCOUNT_NUMBER) ?: "",
                onBack = { navController.navigateUp() },
                onClick = { navController.navigate(Screen.TransactionList.createRoute(accountNumber = it)) }
            )
        }
        composable(
            route = Screen.TransactionList.route,
            arguments = Screen.TransactionList.navArguments
        ) { stack ->
            TransactionList(
                accountNumber = stack.arguments?.getString(Screen.ACCOUNT_NUMBER) ?: "",
            )
        }
    }
}