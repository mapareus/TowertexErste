package com.towertex.erstemodel.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index

@Entity(
    tableName = "transaction_items",
    indices = [
        Index("transaction_id"),
    ],
    primaryKeys = [
        "transaction_id"
    ]
)
data class TransactionItem(
    //artificial
    @ColumnInfo(name = "page")
    val page: Int,
    @ColumnInfo(name = "transaction_id")
    val transactionId: String,
    @ColumnInfo(name = "parent_account_number")
    val parentAccountNumber: String,

    @ColumnInfo(name = "amount_value")
    val amountValue: String,
    @ColumnInfo(name = "amount_precision")
    val amountPrecision: Int,
    @ColumnInfo(name = "amount_currency")
    val amountCurrency: String,
    @ColumnInfo(name = "type")
    val type: String,
    @ColumnInfo(name = "due_date")
    val dueDate: String,
    @ColumnInfo(name = "processing_date")
    val processingDate: String,
    @ColumnInfo(name = "sender_account_number")
    val senderAccountNumber: String,
    @ColumnInfo(name = "sender_bank_code")
    val senderBankCode: String,
    @ColumnInfo(name = "sender_iban")
    val senderIban: String,
    @ColumnInfo(name = "sender_specific_symbol")
    val senderSpecificSymbol: String?,
    @ColumnInfo(name = "sender_specific_symbol_party")
    val senderSpecificSymbolParty: String?,
    @ColumnInfo(name = "sender_variable_symbol")
    val senderVariableSymbol: String?,
    @ColumnInfo(name = "sender_constant_symbol")
    val senderConstantSymbol: String?,
    @ColumnInfo(name = "sender_name")
    val senderName: String?,
    @ColumnInfo(name = "sender_description")
    val senderDescription: String?,
    @ColumnInfo(name = "receiver_account_number")
    val receiverAccountNumber: String,
    @ColumnInfo(name = "receiver_bank_code")
    val receiverBankCode: String,
    @ColumnInfo(name = "receiver_iban")
    val receiverIban: String,
    @ColumnInfo(name = "type_description")
    val typeDescription: String,
)