package com.towertex.erste.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.towertex.erste.R
import com.towertex.erste.theme.ErsteTypography
import com.towertex.erste.viewmodel.AccountDetailViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun AccountDetail(
    accountNumber: String,
    onBack: () -> Unit,
    onClick: (String) -> Unit,
) {
    val viewModel: AccountDetailViewModel = koinViewModel { parametersOf(accountNumber) }
    val name: String = viewModel.name.collectAsStateWithLifecycle().value
    val number: String = viewModel.number.collectAsStateWithLifecycle().value
    val note: String = viewModel.note.collectAsStateWithLifecycle().value
    val balance: String = viewModel.balance.collectAsStateWithLifecycle().value
    val dates: String = viewModel.dates.collectAsStateWithLifecycle().value
    val statements: String = viewModel.statements.collectAsStateWithLifecycle().value

    AccountDetailView(
        onBack = onBack,
        onClick = { onClick(accountNumber) },
        name = name,
        number = number,
        note = note,
        balance = balance,
        dates = dates,
        statements = statements,
    )
}

@Composable
fun AccountDetailView(
    onBack: () -> Unit,
    onClick: () -> Unit,
    name: String,
    number: String,
    note: String,
    balance: String,
    dates: String,
    statements: String,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.card_padding)),
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = name, style = ErsteTypography.h6)
        Text(text = number, style = ErsteTypography.body1)
        Text(text = note, style = ErsteTypography.subtitle1)
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.card_padding)))
        Text(text = balance, style = ErsteTypography.h6)
        Text(text = dates, style = ErsteTypography.body1)
        Text(text = statements, style = ErsteTypography.subtitle1)
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.card_padding)))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Button(onClick = onBack) { Text(text = "Back") }
            Button(onClick = onClick) { Text(text = "Display transactions") }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun AccountDetailViewPreview() {
    AccountDetailView(
        onBack = {},
        onClick = {},
        name = "Dummy Account Name",
        number = "Dummy Account Number",
        note = "Dummy Account Note",
        balance = "Dummy Account Balance",
        dates = "Dummy Account Dates",
        statements = "Dummy Account Statements",
    )
}