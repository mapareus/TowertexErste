package com.towertex.erste.compose

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.towertex.erste.R
import com.towertex.erste.viewmodel.AccountListViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun AccountList(
    onClick: (String) -> Unit
) {
    val viewModel: AccountListViewModel = koinViewModel()
    val pagingAccounts = viewModel.accounts.collectAsLazyPagingItems()

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(all = dimensionResource(R.dimen.card_margin))
    ) {
        items(
            count = pagingAccounts.itemCount,
            key = pagingAccounts.itemKey { it.accountNumber }
        ) { index ->
            val accountItem = pagingAccounts[index] ?: return@items
            AccountCard(
                accountNumber = accountItem.accountNumber,
                onClick = onClick
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PlayerListPreview() {
    AccountList(
        onClick = {}
    )
}