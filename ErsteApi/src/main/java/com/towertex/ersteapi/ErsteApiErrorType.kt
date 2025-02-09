package com.towertex.ersteapi

enum class ErsteApiErrorType {
    REQUEST_TIMEOUT,
    UNAUTHORIZED,
    CONFLICT,
    TOO_MANY_REQUESTS,
    NO_INTERNET,
    PAYLOAD_TOO_LARGE,
    SERVER_ERROR,
    SERIALIZATION,
    UNKNOWN;

    companion object {
        fun fromHttpCode(httpCode: Int): ErsteApiErrorType {
            return when (httpCode) {
                401 -> UNAUTHORIZED
                408 -> REQUEST_TIMEOUT
                409 -> CONFLICT
                412 -> UNAUTHORIZED
                413 -> PAYLOAD_TOO_LARGE
                429 -> TOO_MANY_REQUESTS
                in 500..599 -> SERVER_ERROR
                else -> UNKNOWN
            }
        }
    }
}