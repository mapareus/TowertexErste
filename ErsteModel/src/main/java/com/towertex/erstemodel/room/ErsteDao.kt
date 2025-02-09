package com.towertex.erstemodel.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.towertex.erstemodel.model.AccountItem
import com.towertex.erstemodel.model.TransactionItem

@Dao
interface ErsteDao {
    @Query("SELECT * FROM account_items WHERE page = :page")
    suspend fun getAccounts(page: Int): List<AccountItem>

    @Query("SELECT * FROM account_items")
    suspend fun getAccounts(): List<AccountItem>

    @Query("DELETE FROM account_items WHERE account_number IN (:accountNumbers)")
    suspend fun deleteAccounts(accountNumbers: List<String>)

    @Query("DELETE FROM account_items")
    suspend fun deleteAllAccounts()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAccounts(accounts: List<AccountItem>)

    @Query("SELECT * FROM account_items WHERE account_number = :accountNumber")
    suspend fun getAccount(accountNumber: String): List<AccountItem>

    @Query("SELECT * FROM transaction_items WHERE page = :page AND parent_account_number = :accountNumber")
    suspend fun getTransactions(accountNumber: String, page: Int): List<TransactionItem>

    @Query("SELECT * FROM transaction_items")
    suspend fun getTransactions(): List<TransactionItem>

    @Query("DELETE FROM transaction_items WHERE transaction_id IN (:transactionIds)")
    suspend fun deleteTransactions(transactionIds: List<String>)

    @Query("DELETE FROM transaction_items")
    suspend fun deleteAllTransactions()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransactions(transactions: List<TransactionItem>)

    @Query("SELECT * FROM transaction_items WHERE transaction_id = :transactionId")
    suspend fun getTransaction(transactionId: Int): List<TransactionItem>
}