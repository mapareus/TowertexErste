package com.towertex.erste.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.towertex.erste.R
import com.towertex.erste.viewmodel.TransactionListViewModel
import org.koin.androidx.compose.koinViewModel
import androidx.compose.material.Text
import com.towertex.erste.theme.ErsteTypography
import org.koin.core.parameter.parametersOf

@Composable
fun TransactionList(
    accountNumber: String
) {
    val viewModel: TransactionListViewModel = koinViewModel { parametersOf(accountNumber) }
    val pagingTransactions = viewModel.transactions.collectAsLazyPagingItems()

    LazyColumn(
        contentPadding = PaddingValues(all = dimensionResource(R.dimen.card_margin)),
        modifier = Modifier.fillMaxSize()
    ) {
        items(
            count = pagingTransactions.itemCount,
            key = pagingTransactions.itemKey { it.transactionId }
        ) { index ->
            val transactionItem = pagingTransactions[index] ?: return@items
            Row(
                modifier = Modifier
                    .padding(dimensionResource(R.dimen.card_padding))
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = transactionItem.amountValue, style = ErsteTypography.body1)
                Text(text = transactionItem.amountCurrency, style = ErsteTypography.body1)
            }
        }
    }
}