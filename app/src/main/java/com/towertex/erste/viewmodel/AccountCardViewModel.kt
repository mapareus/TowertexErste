package com.towertex.erste.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.towertex.erste.R
import com.towertex.erste.repository.ResourceRepositoryContract
import com.towertex.erstemodel.repository.TransparentAccountsRepositoryContract
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class AccountCardViewModel(
    private val accountNumber: String,
    private val accountRepository: TransparentAccountsRepositoryContract,
    private val res: ResourceRepositoryContract,
): ViewModel() {
    private val unknown = res.getString(R.string.unknown)

    private val _name = MutableStateFlow(unknown)
    val name: StateFlow<String> get() = _name
    private val _page = MutableStateFlow(unknown)
    val page: StateFlow<String> get() = _page

    init {
        viewModelScope.launch {
            val account = accountRepository.getAccount(accountNumber).filterNotNull().first()
            _name.value = res.getString(R.string.ac_name, account.name)
            _page.value = account.page.toString()
        }
    }
}