package com.towertex.erstemodel.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "account_items",
    indices = [
        Index("account_number"),
    ],
    primaryKeys = [
        "account_number"
    ]
)
data class AccountItem(
    //artificial
    @ColumnInfo(name = "page")
    val page: Int,

    @ColumnInfo(name = "account_number")
    val accountNumber: String,
    @ColumnInfo(name = "bank_code")
    val bankCode: String,
    @ColumnInfo(name = "transparency_from")
    val transparencyFrom: String,
    @ColumnInfo(name = "transparency_to")
    val transparencyTo: String,
    @ColumnInfo(name = "publication_to")
    val publicationTo: String,
    @ColumnInfo(name = "actualization_date")
    val actualizationDate: String,
    @ColumnInfo(name = "balance")
    val balance: String,
    @ColumnInfo(name = "currency")
    val currency: String?,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "description")
    val description: String?,
    @ColumnInfo(name = "note")
    val note: String?,
    @ColumnInfo(name = "iban")
    val iban: String,
    @ColumnInfo(name = "statements")
    val statements: String?,
)