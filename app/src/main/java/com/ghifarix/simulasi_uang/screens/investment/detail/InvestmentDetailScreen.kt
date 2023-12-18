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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ghifarix.simulasi_uang.R
import com.ghifarix.simulasi_uang.components.DetailLoanText
import com.ghifarix.simulasi_uang.components.TitleText
import com.ghifarix.simulasi_uang.components.TopAppBack
import com.ghifarix.simulasi_uang.model.GeneratePdf
import com.ghifarix.simulasi_uang.components.DetailLoanItemText as DetailLoanItemText1

@Composable
fun InvestmentDetailScreen(
    viewModel: InvestmentDetailViewModel = hiltViewModel(),
    onBack: () -> Unit = {}
) {

    val interstitialAd = viewModel.interstitialAd.collectAsState()
    val state = viewModel.state.collectAsState().value
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.getInvestment()
    }

    Scaffold(topBar = {
        TopAppBack(
            context = context,
            interstitialAd = interstitialAd,
            title = stringResource(id = R.string.investment_detail_title),
            onBack = onBack,
            initAds = viewModel::updateInterstitialAds,
            generatePdf = GeneratePdf.INVESTASI,
            showingAds = {
                interstitialAd.value?.show(it)
            })
    }) {
        when (state) {
            InvestmentDetailState.Idle -> {}
            is InvestmentDetailState.Load -> {
                val investment = state.investment
                Column(
                    modifier = Modifier
                        .padding(it)
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
                            DetailLoanText(title = "Modal Awal", text = "Rp $baseInvestment")
                            DetailLoanText(title = "Lama Investasi", text = "$investmentTime")
                            DetailLoanText(
                                title = "Penambahan Modal",
                                text = "Rp $increaseInvestment"
                            )
                            DetailLoanText(title = "Lama Penambahan", text = "$increaseTime")
                            DetailLoanText(title = "Total Investment", text = "Rp $totalInvestment")
                            DetailLoanText(title = "Investasi Rate", text = "$investmentRate%")
                            DetailLoanText(title = "Pajak", text = "$tax %")
                            DetailLoanText(title = "Pertambahan ", text = "$percentageIncrease %")
                            DetailLoanText(title = "Pertambahan ", text = "Rp $amountIncrease")
                        }
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .horizontalScroll(state = rememberScrollState())
                    ) {
                        Row(
                            Modifier
                                .padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
                                .background(MaterialTheme.colorScheme.secondary)
                                .padding(4.dp)
                        ) {
                            Text(
                                text = "Tahun",
                                modifier = Modifier.width(64.dp),
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            TitleText(text = "Pertambahan")
                            Spacer(modifier = Modifier.width(10.dp))
                            TitleText(text = "Pajak")
                            Spacer(modifier = Modifier.width(10.dp))
                            TitleText(text = "Investasi")
                        }
                        LazyColumn() {
                            items(state.investment.investmentItem.size) { pos ->
                                val item = state.investment.investmentItem[pos]
                                val isLast = pos == state.investment.investmentItem.size - 1
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
                                    DetailLoanItemText1(text = item.investment, isLast = isLast)
                                    Spacer(modifier = Modifier.width(15.dp))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}