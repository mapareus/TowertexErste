package com.towertex.ersteapi

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.serialization.SerializationException
import com.towertex.ersteapi.ErsteApiErrorType.NO_INTERNET
import com.towertex.ersteapi.ErsteApiErrorType.SERIALIZATION
import com.towertex.ersteapi.model.ErrorResponse

suspend inline fun <reified T> HttpClient.toResult(
    getResponseBlock: HttpClient.() -> HttpResponse
): ErsteApiResult<T> =
    try {
        getResponseBlock().run {
            status.value.let {
                when (it) {
                    in 200..299 -> Success(body<T>())
                    else -> Error(ErsteApiErrorType.fromHttpCode(it), it, body())
                }
            }
        }
    } catch(e: UnresolvedAddressException) {
        Exception(NO_INTERNET)
    } catch(e: SerializationException) {
        Exception(SERIALIZATION)
    }

sealed interface ErsteApiResult<out D>
data class Success<out D>(val data: D): ErsteApiResult<D>
data class Error<out D>(val apiErrorType: ErsteApiErrorType, val httpCode: Int, val error: ErrorResponse): ErsteApiResult<D>
data class Exception<out D>(val apiErrorType: ErsteApiErrorType): ErsteApiResult<D>