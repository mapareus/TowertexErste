package com.towertex.erstemodel

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.towertex.ersteapi.ErsteApiBuilder
import com.towertex.ersteapi.services.TransparentAccountsApiContract
import com.towertex.erstemodel.repository.TransparentAccountsRepositoryContract
import com.towertex.erstemodel.repository.TransparentTransactionsRepositoryContract
import com.towertex.erstemodel.room.ErsteDatabase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before

@RunWith(AndroidJUnit4::class)
class ErsteModelInstrumentedTest {
    @Test
    fun dummyTest() {
        // Context of the app under test.
        assertEquals("com.towertex.erstemodel.test", appContext.packageName)
    }

    companion object {
        //TODO api seems to be returning 50 regardless of the requested page size
        private const val EXPECTED_PAGE_SIZE = 50

        private lateinit var appContext: Context
        private lateinit var api: TransparentAccountsApiContract
        private lateinit var db: ErsteDatabase
        private lateinit var accountsRepository: TransparentAccountsRepositoryContract
        private lateinit var transactionsRepository: TransparentTransactionsRepositoryContract
    }

    @Before
    fun init(): Unit = runBlocking {
        appContext = InstrumentationRegistry.getInstrumentation().targetContext
        api = ErsteApiBuilder {
            setPageSize(EXPECTED_PAGE_SIZE)
            setDebugLogger()
            setLogAll()
        }.build()
        db = ErsteDatabase.buildDatabase(appContext)
        db.ersteDao.deleteAllAccounts()
        ErsteRepository(api, db).also {
            accountsRepository = it
            transactionsRepository = it
        }

    }

    @Test
    fun testAccounts(): Unit = runBlocking {
        try {
            //first we should get just one response from network
            var i = 0
            accountsRepository.getAccounts(1).onEach{
                i++
                assertEquals(EXPECTED_PAGE_SIZE, it.size)
                assertEquals(1, i)
                assertEquals(1, it.first().page)
            }.launchIn(this).join()

            //check whether the data are indeed in the database
            val dbItems = db.ersteDao.getAccounts(1)
            assertEquals(EXPECTED_PAGE_SIZE, dbItems.size)
            assertEquals(1, dbItems.first().page)

            //artificially delete one item from db
            db.ersteDao.deleteAccounts(listOf(dbItems.first().accountNumber))

            //we should obtain first the incomplete list from database and second should be the updated list from network
            i = 0
            accountsRepository.getAccounts(1).onEach{
                i++
                when (i) {
                    1 -> assertEquals(EXPECTED_PAGE_SIZE-1, it.size)
                    2 -> assertEquals(EXPECTED_PAGE_SIZE, it.size)
                    else -> fail("there should be just two values")
                }
                assertEquals(1, it.first().page)
            }.launchIn(this)
        } catch (e: AssertionError) {
            throw e
        } catch (e: Throwable) {
            fail("should not fail $e")
        }
    }

    @Test
    fun testTransactions(): Unit = runBlocking {
        try {
            //first we need to get the accounts to have some valid account number
            var accountNumber = ""
            accountsRepository.getAccounts(1).onEach {
                accountNumber = it.first().accountNumber
            }.launchIn(this).join()

            //then we should get just one response from network
            var i = 0
            transactionsRepository.getTransactions(accountNumber, 1).onEach{
                i++
                assertEquals(EXPECTED_PAGE_SIZE, it.size)
                assertEquals(1, i)
                assertEquals(1, it.first().page)
            }.launchIn(this).join()

            //check whether the data are indeed in the database
            val dbItems = db.ersteDao.getTransactions(accountNumber, 1)
            assertEquals(EXPECTED_PAGE_SIZE, dbItems.size)
            assertEquals(1, dbItems.first().page)

            //artificially delete one item from db
            db.ersteDao.deleteTransactions(listOf(dbItems.first().transactionId))

            //we should obtain first the incomplete list from database and second should be the updated list from network
            i = 0
            transactionsRepository.getTransactions(accountNumber, 1).onEach{
                i++
                when (i) {
                    1 -> assertEquals(EXPECTED_PAGE_SIZE-1, it.size)
                    2 -> assertEquals(EXPECTED_PAGE_SIZE, it.size)
                    else -> fail("there should be just two values")
                }
                assertEquals(1, it.first().page)
            }.launchIn(this)
        } catch (e: AssertionError) {
            throw e
        } catch (e: Throwable) {
            fail("should not fail $e")
        }
    }
}