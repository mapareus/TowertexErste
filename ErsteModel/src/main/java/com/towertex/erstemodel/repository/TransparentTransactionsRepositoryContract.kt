package com.towertex.erstemodel.repository

import com.towertex.erstemodel.model.TransactionItem
import kotlinx.coroutines.flow.Flow

interface TransparentTransactionsRepositoryContract {
    fun getTransactions(accountNumber: String, page: Int): Flow<List<TransactionItem>>
    fun getTransaction(transactionId: Int): Flow<TransactionItem?>
}