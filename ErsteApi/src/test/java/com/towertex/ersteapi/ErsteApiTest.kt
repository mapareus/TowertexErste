package com.towertex.ersteapi

import com.towertex.ersteapi.model.TransparentAccountsResponse
import com.towertex.ersteapi.model.TransparentTransactionsResponse
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.SerializationException
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

class ErsteApiTest {
    @Test
    fun dummyTest() {
        assertEquals(4, 2 + 2)
    }

    companion object {
        private const val API_TRANSPARENT_ACCOUNTS = "https://webapi.developers.erstegroup.com/api/csas/public/sandbox/v3/transparentAccounts"
        private const val API_PAGE_SIZE = 25
        //TODO it seems the api is returning 50 regardless to what is requested
        private const val API_PAGE_SIZE_ACTUAL = 50

        private lateinit var api: ErsteApi
        private lateinit var wrongApi: ErsteApi
    }

    @Before
    fun init() {
        api = ErsteApiBuilder {
            setLogAll()
            setDebugLogger()
        }.build()
        wrongApi = ErsteApiBuilder {
            setBaseUrl(API_TRANSPARENT_ACCOUNTS)
            setApiKey("wrong key")
            setLogInfo()
            setLogHeaders()
            setLogBody()
            setLogNone()
            setLogAll()
            setReleaseLogger()
            setDebugLogger()
            setPageSize(API_PAGE_SIZE)
        }.build()
    }

    @Test
    fun `request will fail when authentication is wrong`() = runBlocking {
        val result = wrongApi.getAccounts(1)
        assertNotNull("result should be Error", result)
        assertTrue("result should be Error", result is Error)
        assertEquals(ErsteApiErrorType.UNAUTHORIZED, (result as? Error)?.apiErrorType)
        assertEquals(412, (result as? Error)?.httpCode)
    }

    @Test
    fun `getTransparentAccounts returns valid result`() = runBlocking {
        val result = api.getAccounts(1)
        assertTrue("result should be Success", result is Success)
        val response = (result as? Success<*>)?.data as? TransparentAccountsResponse
        val message = "response should not be empty"
        assertNotNull(message, response)
        assertEquals(message,1, response?.nextPage)
        assertEquals(message,0, response?.pageNumber)
        //TODO it seems the api is returning 50 regardless to what is requested
        assertEquals(message, API_PAGE_SIZE_ACTUAL, response?.pageSize)
        assertEquals(message, API_PAGE_SIZE_ACTUAL, response?.accounts?.size)
    }

    @Test
    fun `api UnresolvedAddressException should return NO_INTERNET`(): Unit = runBlocking {
        val result = HttpClient(Android).toResult<String> { throw UnresolvedAddressException() }
        assertTrue("result should be Exception", result is Exception)
        assertEquals(ErsteApiErrorType.NO_INTERNET, (result as? Exception)?.apiErrorType)
    }

    @Test
    fun `api SerializationException should return SERIALIZATION`(): Unit = runBlocking {
        val result = HttpClient(Android).toResult<String> { throw SerializationException() }
        assertTrue("result should be Exception", result is Exception)
        assertEquals(ErsteApiErrorType.SERIALIZATION, (result as? Exception)?.apiErrorType)
    }

    @Test
    fun `getTransparentTransactions returns valid result`() = runBlocking {
        val result1 = api.getAccounts(1)
        assertTrue("result should be Success", result1 is Success)
        val response1 = (result1 as? Success<*>)?.data as? TransparentAccountsResponse
        val message = "response should not be empty"
        assertNotNull(message, response1)
        val result = api.getTransactions(response1!!.accounts.first().accountNumber)
        assertTrue("result should be Success", result is Success)
        val response = (result as? Success<*>)?.data as? TransparentTransactionsResponse
        assertNotNull(message, response)
        assertEquals(message,1, response?.nextPage)
        assertEquals(message,0, response?.pageNumber)
        //TODO it seems the api is returning 50 regardless to what is requested
        assertEquals(message, API_PAGE_SIZE_ACTUAL, response?.pageSize)
        assertEquals(message, API_PAGE_SIZE_ACTUAL, response?.transactions?.size)
    }
}