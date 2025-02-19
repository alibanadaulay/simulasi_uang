package com.ghifarix.simulasi_uang.screens.investment.detail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ghifarix.simulasi_uang.R
import com.ghifarix.simulasi_uang.components.DetailLoanText
import com.ghifarix.simulasi_uang.components.TitleText
import com.ghifarix.simulasi_uang.components.TopAppBack
import com.ghifarix.simulasi_uang.model.GeneratePdf
import com.ghifarix.simulasi_uang.screens.investment.model.Investment
import com.ghifarix.simulasi_uang.screens.investment.model.InvestmentItem
import com.ghifarix.simulasi_uang.components.DetailLoanItemText as DetailLoanItemText1

@Composable
fun InvestmentDetailScreen(
    viewModel: InvestmentDetailViewModel = hiltViewModel(),
    onBack: () -> Unit = {}
) {

    val state = viewModel.state.collectAsState().value
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.getInvestment()
    }

    Scaffold(topBar = {
        TopAppBack(
            context = context,
            title = stringResource(id = R.string.investment_detail_title),
            onBack = onBack,
            generatePdf = GeneratePdf.INVESTASI,
            )
    }) {
        when (state) {
            InvestmentDetailState.Idle -> {}

            is InvestmentDetailState.Load -> {
                LoadDataSuccess(modifier = Modifier.padding(it), investment = state.investment)
            }
        }
    }
}

@Composable
@Preview
fun LoadDataSuccessPreview() {
    LoadDataSuccess(investment = Investment())
}

@Composable
fun LoadDataSuccess(
    modifier: Modifier = Modifier,
    investment: Investment
) {
    Column(
        modifier = modifier
    ) {
        Card(
            modifier = Modifier
                .padding(all = 8.dp)
                .fillMaxWidth()
                .shadow(1.dp),
            colors = CardDefaults.outlinedCardColors(),
            border = BorderStroke(1.dp, color = Color.Gray)
        ) {
            with(investment) {
                DetailLoanText(
                    title = stringResource(id = R.string.initial_capital),
                    text = baseInvestment
                )
                DetailLoanText(
                    title = stringResource(id = R.string.investment_duration),
                    text = "$investmentTime ${stringResource(id = R.string.year)}"
                )
                DetailLoanText(
                    text = increaseInvestment,
                    title = stringResource(id = R.string.capital_increase)
                )
                DetailLoanText(
                    title = stringResource(id = R.string.addition_time),
                    text = "$increaseTime ${stringResource(id = R.string.year)}"
                )
                DetailLoanText(
                    title = stringResource(id = R.string.total_investment),
                    text = totalInvestment
                )
                DetailLoanText(title = "Investasi Rate", text = "$investmentRate%")
                DetailLoanText(
                    title = stringResource(id = R.string.tax),
                    text = "$tax %"
                )
                DetailLoanText(
                    title = stringResource(id = R.string.increase),
                    text = "$percentageIncrease %"
                )
                DetailLoanText(
                    title = stringResource(id = R.string.increase),
                    text = amountIncrease
                )
            }
        }
        ShowListInvestmentItems(investmentList = investment.investmentItem)
    }
}

@Composable
fun ShowListInvestmentItems(investmentList: List<InvestmentItem>) {
    Column(
        modifier = Modifier
            .padding(top = 8.dp)
            .fillMaxWidth()
            .fillMaxHeight()
            .horizontalScroll(state = rememberScrollState())
    ) {
        Row(
            modifier = Modifier
                .padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
                .background(MaterialTheme.colorScheme.secondary)
                .padding(4.dp),
            verticalAlignment = Alignment.CenterVertically

        ) {
            Text(
                text = stringResource(id = R.string.year),
                modifier = Modifier.width(64.dp),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.width(10.dp))
            TitleText(text = stringResource(id = R.string.increase))
            Spacer(modifier = Modifier.width(10.dp))
            TitleText(text = stringResource(id = R.string.tax))
            Spacer(modifier = Modifier.width(10.dp))
            TitleText(text = stringResource(id = R.string.capital_increase))
            Spacer(modifier = Modifier.width(10.dp))
            TitleText(text = stringResource(id = R.string.investment))
        }
        LazyColumn {
            items(investmentList.size) { pos ->
                val item = investmentList[pos]
                val isLast = pos == investmentList.size - 1
                Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                    Text(
                        text = item.time,
                        modifier = Modifier.width(64.dp),
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.width(15.dp))
                    DetailLoanItemText1(
                        text = item.investmentIncrease,
                        isLast = isLast
                    )
                    Spacer(modifier = Modifier.width(15.dp))
                    DetailLoanItemText1(text = item.tax, isLast = isLast)
                    Spacer(modifier = Modifier.width(15.dp))
                    DetailLoanItemText1(text = item.increaseCapital, isLast = isLast)
                    Spacer(modifier = Modifier.width(15.dp))
                    DetailLoanItemText1(text = item.investment, isLast = isLast)
                    Spacer(modifier = Modifier.width(15.dp))
                }
            }
        }
    }
}