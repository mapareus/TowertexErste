package com.towertex.erste.viewmodel

import com.towertex.erste.CoroutinesTestRule
import com.towertex.erste.repository.ResourceRepositoryContract
import com.towertex.erstemodel.repository.TransparentAccountsRepositoryContract
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import com.towertex.erste.R
import com.towertex.erstemodel.model.AccountItem
import io.mockk.coEvery
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class AccountCardViewModelTest {

    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    private lateinit var accountRepository: TransparentAccountsRepositoryContract
    private lateinit var res: ResourceRepositoryContract

    @Before
    fun setUp() {
        accountRepository = mockk()
        res = mockk()
    }

    @Test
    fun `test account card name is loaded correctly`() = runTest {
        //Arrange
        val account = AccountItem(1, "123-456-789", "007", "2004-01-01", "2021-01-01", "2010-01-01", "2025-01-01", "100.25", "CZK", "some name", null, null, "ABC1234", null)
        coEvery { accountRepository.getAccount(any()) } returns flowOf(account)
        every { res.getString(R.string.unknown) } returns "Unknown"
        every { res.getString(R.string.ac_name, any()) } answers { "Account: ${account.name}" }

        //Act
        val viewModel = AccountCardViewModel("123-456-789", accountRepository, res)
        advanceUntilIdle()

        //Assert
        assertEquals("Account: some name", viewModel.name.value)
        assertEquals("1", viewModel.page.value)
    }
}