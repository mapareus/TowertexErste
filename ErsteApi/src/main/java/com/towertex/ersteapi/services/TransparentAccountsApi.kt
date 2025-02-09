package com.towertex.ersteapi.services

import com.towertex.ersteapi.model.TransparentAccountsResponse
import com.towertex.ersteapi.model.TransparentTransactionsResponse
import com.towertex.ersteapi.toResult
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.url

class TransparentAccountsApi(
    private val httpClient: HttpClient,
    private val headerAuth: Pair<String, String>,
    private val baseUrl: String,
    private val pageSize: Int,
): TransparentAccountsApiContract {

    override suspend fun getAccounts(
        page: Int,
        size: Int?,
        filter: String?
    ) = httpClient.toResult<TransparentAccountsResponse> {
        get {
            url(StringBuilder()
                .append(baseUrl)
                .append("transparentAccounts/?page=")
                .append(if (page > 0) page - 1 else 0)
                .append("&size=")
                .append(size ?: pageSize)
                .apply { filter?.also {
                    append("&filter=")
                    append(it)
                } }
                .toString()
            )
            headers {
                append(headerAuth.first, headerAuth.second)
            }
        }
    }

    override suspend fun getTransactions(
        accountNumber: String,
        page: Int,
        size: Int?,
        filter: String?
    ) = httpClient.toResult<TransparentTransactionsResponse> {
        get {
            url(StringBuilder()
                .append(baseUrl)
                .append("transparentAccounts/")
                .append(accountNumber)
                .append("/transactions/?page=")
                .append(if (page > 0) page - 1 else 0)
                .append("&size=")
                .append(size ?: pageSize)
                .apply { filter?.also {
                    append("&filter=")
                    append(it)
                } }
                .toString()
            )
            headers {
                append(headerAuth.first, headerAuth.second)
            }
        }
    }
}