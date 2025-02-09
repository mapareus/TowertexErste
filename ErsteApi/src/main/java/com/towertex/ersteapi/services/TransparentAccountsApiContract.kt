package com.towertex.ersteapi.services

import com.towertex.ersteapi.ErsteApiResult
import com.towertex.ersteapi.model.TransparentAccountsResponse
import com.towertex.ersteapi.model.TransparentTransactionsResponse

interface TransparentAccountsApiContract {

    suspend fun getAccounts(
        page: Int = 1,
        size: Int? = null,
        filter: String? = null,
    ): ErsteApiResult<TransparentAccountsResponse>

    suspend fun getTransactions(
        accountNumber: String,
        page: Int = 1,
        size: Int? = null,
        filter: String? = null,
    ): ErsteApiResult<TransparentTransactionsResponse>
}