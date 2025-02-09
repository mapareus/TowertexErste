package com.towertex.ersteapi.model

import kotlinx.serialization.Serializable

@Serializable
data class TransparentAccountsResponse(
    val pageNumber: Int,
    val pageCount: Int,
    val pageSize: Int,
    val recordCount: Int,
    val nextPage: Int,
    val accounts: List<AccountObject>,
) {
    @Serializable
    data class AccountObject(
        val accountNumber: String,
        val bankCode: String,
        val transparencyFrom: String,
        val transparencyTo: String,
        val publicationTo: String,
        val actualizationDate: String,
        val balance: String,
        val currency: String?,
        val name: String,
        val description: String?,
        val note: String?,
        val iban: String,
        val statements: List<String>?,
    )
}