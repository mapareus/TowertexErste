package com.towertex.erste.repository

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.towertex.erste.di.FIRST_PAGE
import com.towertex.erste.di.PAGE_SIZE
import com.towertex.erstemodel.model.TransactionItem
import com.towertex.erstemodel.repository.TransparentTransactionsRepositoryContract
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

class TransactionPagingSource(
    private val accountNumber: String,
    private val repository: TransparentTransactionsRepositoryContract
) : PagingSource<Int, TransactionItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TransactionItem> {
        return try {
            val pageNumber: Int = params.key ?: FIRST_PAGE
            val response = withContext(Dispatchers.IO) { repository.getTransactions(accountNumber, pageNumber).first() }
            //TODO Timber
            Log.d("data", "$pageNumber ... ${response.size}")
            LoadResult.Page(
                data = response,
                prevKey = if (pageNumber == FIRST_PAGE) null else pageNumber - 1,
                nextKey = if (response.size < PAGE_SIZE) null else pageNumber + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, TransactionItem>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        //TODO compare behavior
//        return state.closestPageToPosition(anchorPosition)?.prevKey
        val pageIndex = anchorPosition / PAGE_SIZE
        return pageIndex
    }
}