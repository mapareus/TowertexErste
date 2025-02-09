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

class AccountDetailViewModel(
    private val accountNumber: String,
    private val accountRepository: TransparentAccountsRepositoryContract,
    private val res: ResourceRepositoryContract,
): ViewModel() {
    private val unknown = res.getString(R.string.unknown)

    private val _name = MutableStateFlow(unknown)
    val name: StateFlow<String> get() = _name
    private val _number = MutableStateFlow(unknown)
    val number: StateFlow<String> get() = _number
    private val _note = MutableStateFlow(unknown)
    val note: StateFlow<String> get() = _note
    private val _balance = MutableStateFlow(unknown)
    val balance: StateFlow<String> get() = _balance
    private val _dates = MutableStateFlow(unknown)
    val dates: StateFlow<String> get() = _dates
    private val _statements = MutableStateFlow(unknown)
    val statements: StateFlow<String> get() = _statements

    init {
        viewModelScope.launch {
            val account = accountRepository.getAccount(accountNumber).filterNotNull().first()
            _name.value = res.getString(R.string.ad_name, account.name, account.description ?: unknown)
            _number.value = res.getString(R.string.ad_number, account.accountNumber, account.bankCode, account.iban)
            _note.value = res.getString(R.string.ad_note, account.note ?: unknown, account.actualizationDate)
            _balance.value = res.getString(R.string.ad_balance, account.balance, account.currency ?: unknown)
            _dates.value = res.getString(R.string.ad_dates, account.publicationTo, account.transparencyFrom, account.transparencyTo)
            _statements.value = res.getString(R.string.ad_statements, account.statements ?: unknown)
        }
    }
}