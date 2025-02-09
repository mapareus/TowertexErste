package com.towertex.ersteapi.model

import kotlinx.serialization.Serializable

@Serializable
data class TransparentTransactionsResponse(
    val pageNumber: Int,
    val pageCount: Int,
    val pageSize: Int,
    val recordCount: Int,
    val nextPage: Int,
    val transactions: List<TransactionObject>,
) {
    @Serializable
    data class TransactionObject(
        val amount: AmountObject,
        val type: String,
        val dueDate: String,
        val processingDate: String,
        val sender: SenderObject,
        val receiver: ReceiverObject,
        val typeDescription: String,
    ) {
        @Serializable
        data class AmountObject(
            val value: String,
            val precision: Int,
            val currency: String,
        )
        @Serializable
        data class SenderObject(
            val accountNumber: String,
            val bankCode: String,
            val iban: String,
            val specificSymbol: String?,
            val specificSymbolParty: String?,
            val variableSymbol: String?,
            val constantSymbol: String?,
            val name: String?,
            val description: String?,
        )
        @Serializable
        data class ReceiverObject(
            val accountNumber: String,
            val bankCode: String,
            val iban: String,
        )
    }
}