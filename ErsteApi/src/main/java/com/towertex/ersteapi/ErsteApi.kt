package com.towertex.ersteapi

import com.towertex.ersteapi.services.TransparentAccountsApiContract
import com.towertex.ersteapi.services.TransparentAccountsApi
import io.ktor.client.HttpClient

//server api is typically divided into groups of services, here each group is implemented in separate delegate
class ErsteApi internal constructor(
    httpClient: HttpClient,
    headerAuth: Pair<String, String>,
    baseUrl: String,
    pageSize: Int
):
        TransparentAccountsApiContract by TransparentAccountsApi(httpClient, headerAuth, baseUrl, pageSize)