package com.towertex.erstemodel.repository

import com.towertex.erstemodel.model.AccountItem
import kotlinx.coroutines.flow.Flow

interface TransparentAccountsRepositoryContract {
    fun getAccounts(page: Int): Flow<List<AccountItem>>
    fun getAccount(accountNumber: String): Flow<AccountItem?>
}