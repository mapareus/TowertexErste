package com.towertex.erste.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.towertex.erste.di.PAGE_SIZE
import com.towertex.erste.repository.TransactionPagingSource
import com.towertex.erstemodel.model.TransactionItem
import com.towertex.erstemodel.repository.TransparentTransactionsRepositoryContract
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

class TransactionListViewModel(
    accountNumber: String,
    repository: TransparentTransactionsRepositoryContract,
): ViewModel() {
    private val _transactions = MutableStateFlow<PagingData<TransactionItem>?>(null)
    val transactions: Flow<PagingData<TransactionItem>> get() = _transactions.filterNotNull()

    init {
        viewModelScope.launch {
            Pager(
                config = PagingConfig(pageSize = PAGE_SIZE),
                pagingSourceFactory = { TransactionPagingSource(accountNumber = accountNumber, repository = repository) }
            ).flow.cachedIn(viewModelScope).collect {
                _transactions.value = it
            }
        }
    }
}