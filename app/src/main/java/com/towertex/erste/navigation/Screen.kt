package com.towertex.erste.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Screen(
    val route: String,
    val navArguments: List<NamedNavArgument> = emptyList(),
) {
    companion object {
        private const val ACCOUNT_DETAIL = "accountDetail/"
        const val ACCOUNT_NUMBER = "accountNumber"
        private const val TRANSACTION_LIST = "transactionList/"
    }

    data object Home : Screen("home")

    data object AccountDetail : Screen(
        route = "$ACCOUNT_DETAIL{$ACCOUNT_NUMBER}",
        navArguments = listOf(navArgument(ACCOUNT_NUMBER) {
            type = NavType.StringType
        })
    ) {
        fun createRoute(accountNumber: String) = "$ACCOUNT_DETAIL${accountNumber}"
    }

    data object TransactionList : Screen(
        route = "$TRANSACTION_LIST{$ACCOUNT_NUMBER}",
        navArguments = listOf(navArgument(ACCOUNT_NUMBER) {
            type = NavType.StringType
        })
    ) {
        fun createRoute(accountNumber: String) = "$TRANSACTION_LIST${accountNumber}"
    }
}