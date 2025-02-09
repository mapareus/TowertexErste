package com.towertex.erstemodel.repository

import com.towertex.ersteapi.Success
import com.towertex.ersteapi.model.TransparentAccountsResponse
import com.towertex.ersteapi.services.TransparentAccountsApiContract
import com.towertex.erstemodel.model.AccountItem
import com.towertex.erstemodel.room.ErsteDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TransparentAccountsRepository(
    private val api: TransparentAccountsApiContract,
    private val dao: ErsteDao
): TransparentAccountsRepositoryContract {
    override fun getAccounts(
        page: Int
    ) = flow {
        val currentAccounts = dao.getAccounts(page)
        if (currentAccounts.isNotEmpty()) emit(currentAccounts)
        val newAccounts = (api.getAccounts(page) as? Success)?.data?.accounts ?: emptyList()
        if (newAccounts.isEmpty()) {
            if (currentAccounts.isNotEmpty()) return@flow
            emit(emptyList())
            return@flow
        }
        if (currentAccounts.equals(newAccounts, page)) return@flow
        val transformedNewAccounts = newAccounts.map { it.toAccountItem(page) }
        dao.deleteAccounts(currentAccounts.map { it.accountNumber })
        dao.insertAccounts(transformedNewAccounts)
        emit(transformedNewAccounts)
    }

    private fun List<AccountItem>.equals(new: List<TransparentAccountsResponse.AccountObject>, page: Int): Boolean {
        if (size != new.size) return false
        sortedBy { it.accountNumber }.zip(new.sortedBy { it.accountNumber }).forEach {
            if (it.first.page != page) return false
            if (it.first.accountNumber != it.second.accountNumber) return false
        }
        return true
    }

    private fun TransparentAccountsResponse.AccountObject.toAccountItem(page: Int) = AccountItem(
        page,
        accountNumber,
        bankCode,
        transparencyFrom,
        transparencyTo,
        publicationTo,
        actualizationDate,
        balance,
        currency,
        name,
        description,
        note,
        iban,
        statements?.toJson()
    )

    override fun getAccount(accountNumber: String): Flow<AccountItem?> = flow {
        emit(dao.getAccount(accountNumber).firstOrNull())
    }
}