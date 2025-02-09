package com.towertex.erstemodel.repository

import com.towertex.ersteapi.Success
import com.towertex.ersteapi.model.TransparentTransactionsResponse
import com.towertex.ersteapi.services.TransparentAccountsApiContract
import com.towertex.erstemodel.model.TransactionItem
import com.towertex.erstemodel.room.ErsteDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TransparentTransactionsRepository(
    private val api: TransparentAccountsApiContract,
    private val dao: ErsteDao
): TransparentTransactionsRepositoryContract {

    override fun getTransactions(
        accountNumber: String,
        page: Int
    ) = flow {
        val currentTransactions = dao.getTransactions(accountNumber, page)
        if (currentTransactions.isNotEmpty()) emit(currentTransactions)
        val newTransactions =
            (api.getTransactions(accountNumber, page) as? Success)?.data?.transactions
                ?: emptyList()
        if (newTransactions.isEmpty()) {
            if (currentTransactions.isNotEmpty()) return@flow
            emit(emptyList())
            return@flow
        }
        if (currentTransactions.equals(newTransactions, accountNumber, page)) return@flow
        val transformedNewTransactions =
            newTransactions.map { it.toTransactionItem(accountNumber, page) }
        dao.deleteTransactions(currentTransactions.map { it.transactionId })
        dao.insertTransactions(transformedNewTransactions)
        emit(transformedNewTransactions)
    }

    private fun List<TransactionItem>.equals(
        new: List<TransparentTransactionsResponse.TransactionObject>,
        accountNumber: String,
        page: Int
    ): Boolean {
        if (size != new.size) return false
        sortedBy { it.transactionId }.zip(new.sortedBy { it.toTransactionId(accountNumber, page) })
            .forEach {
                if (it.first.page != page) return false
                if (it.first.transactionId != it.second.toTransactionId(
                        accountNumber,
                        page
                    )
                ) return false
            }
        return true
    }

    private fun TransparentTransactionsResponse.TransactionObject.toTransactionId(
        accountNumber: String,
        page: Int
    ) = "$accountNumber-$page-${amount.value}-$processingDate"

    private fun TransparentTransactionsResponse.TransactionObject.toTransactionItem(
        accountNumber: String,
        page: Int
    ) = TransactionItem(
        page = page,
        transactionId = toTransactionId(accountNumber, page),
        parentAccountNumber = accountNumber,
        amountValue = amount.value,
        amountPrecision = amount.precision,
        amountCurrency = amount.currency,
        type = type,
        dueDate = dueDate,
        processingDate = processingDate,
        senderAccountNumber = sender.accountNumber,
        senderBankCode = sender.bankCode,
        senderIban = sender.iban,
        senderSpecificSymbol = sender.specificSymbol,
        senderSpecificSymbolParty = sender.specificSymbolParty,
        senderVariableSymbol = sender.variableSymbol,
        senderConstantSymbol = sender.constantSymbol,
        senderName = sender.name,
        senderDescription = sender.description,
        receiverAccountNumber = receiver.accountNumber,
        receiverBankCode = receiver.bankCode,
        receiverIban = receiver.iban,
        typeDescription = typeDescription,
    )

    override fun getTransaction(transactionId: Int): Flow<TransactionItem?> = flow {
        emit(dao.getTransaction(transactionId).firstOrNull())
    }
}