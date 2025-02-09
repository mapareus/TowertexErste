package com.towertex.ersteapi.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    val status: String,
    @SerialName("cz-transactionId") val transactionId: String? = null,
) {
    @Serializable
    data class Error(
        val error: String,
        val scope: String,
        val parameters: List<Parameter>,
    ) {
        @Serializable
        data class Parameter(
            @SerialName("AMOUNT_ENTERED") val amountEntered: Int,
            @SerialName("CURRENCY") val currency: String,
            @SerialName("LIMIT") val limit: String,
        )
    }
}