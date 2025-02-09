package com.towertex.erste.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.towertex.erste.R
import com.towertex.erste.theme.ErsteTypography
import com.towertex.erste.viewmodel.AccountCardViewModel
import org.koin.compose.koinInject
import org.koin.core.parameter.parametersOf

@Composable
fun AccountCard(
    accountNumber: String,
    onClick: (String) -> Unit,
) {
    val viewModel: AccountCardViewModel = koinInject { parametersOf(accountNumber) }
    val name: String = viewModel.name.collectAsStateWithLifecycle().value
    val page: String = viewModel.page.collectAsStateWithLifecycle().value

    AccountCardView(
        onClick = { onClick(accountNumber) },
        name = name,
        page = page,
    )
}

@Composable
fun AccountCardView(
    onClick: () -> Unit,
    name: String,
    page: String
) {
    Card(
        elevation = dimensionResource(R.dimen.card_elevation),
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.card_margin))
            .clickable { onClick() },
    ) {
        Column(modifier = Modifier.padding(dimensionResource(R.dimen.card_padding))) {
            Text(text = name, style = ErsteTypography.body1)
            Text(text = page, style = ErsteTypography.subtitle1,
                textAlign = TextAlign.End, modifier = Modifier.fillMaxWidth())
        }
    }
}


@Preview(showBackground = true)
@Composable
fun AccountCardViewPreview() {
    AccountCardView(
        onClick = {},
        name = "Account Name",
        page = "0"
    )
}