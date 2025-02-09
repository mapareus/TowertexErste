package com.towertex.erste.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.towertex.erste.di.PAGE_SIZE
import com.towertex.erste.repository.AccountPagingSource
import com.towertex.erstemodel.model.AccountItem
import com.towertex.erstemodel.repository.TransparentAccountsRepositoryContract
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

class AccountListViewModel(
    accountRepository: TransparentAccountsRepositoryContract,
): ViewModel() {
    private val _accounts = MutableStateFlow<PagingData<AccountItem>?>(null)
    val accounts: Flow<PagingData<AccountItem>> get() = _accounts.filterNotNull()

    init {
        viewModelScope.launch {
            Pager(
                config = PagingConfig(pageSize = PAGE_SIZE),
                pagingSourceFactory = { AccountPagingSource(repository = accountRepository) }
            ).flow.cachedIn(viewModelScope).collect {
                _accounts.value = it
            }
        }
    }
}