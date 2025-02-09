package com.towertex.erste.di

import com.towertex.erste.repository.ResourceRepository
import com.towertex.erste.repository.ResourceRepositoryContract
import com.towertex.erste.viewmodel.AccountCardViewModel
import com.towertex.erste.viewmodel.AccountDetailViewModel
import com.towertex.erste.viewmodel.AccountListViewModel
import com.towertex.erste.viewmodel.TransactionListViewModel
import com.towertex.ersteapi.ErsteApiBuilder
import com.towertex.ersteapi.services.TransparentAccountsApiContract
import com.towertex.erstemodel.ErsteRepository
import com.towertex.erstemodel.repository.TransparentAccountsRepositoryContract
import com.towertex.erstemodel.repository.TransparentTransactionsRepositoryContract
import com.towertex.erstemodel.room.ErsteDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.binds
import org.koin.dsl.module

//TODO the api is returning 50 always regardless of the requested page size
const val PAGE_SIZE = 50
const val FIRST_PAGE = 1

val repositoryModule = module {
    single { ErsteDatabase.buildDatabase(context = get()) }
    single { ErsteApiBuilder { setPageSize(PAGE_SIZE) }.build() } bind TransparentAccountsApiContract::class
    single { ErsteRepository(transparentAccountsApi = get(), db = get()) } binds(arrayOf(TransparentAccountsRepositoryContract::class, TransparentTransactionsRepositoryContract::class))
    single { ResourceRepository(androidContext()) } bind ResourceRepositoryContract::class
}

val viewModelModule = module {
    viewModel { AccountListViewModel(accountRepository = get()) }
    viewModel { (accountNumber: String) -> AccountCardViewModel(accountNumber, accountRepository = get(), res = get()) }
    viewModel { (accountNumber: String) -> AccountDetailViewModel(accountNumber, accountRepository = get(), res = get()) }
    viewModel { (accountNumber: String) -> TransactionListViewModel(accountNumber, repository = get()) }
}