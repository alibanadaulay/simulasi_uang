package com.ghifarix.simulasi_uang.screens.investment.detail

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ghifarix.simulasi_uang.R
import com.ghifarix.simulasi_uang.components.TopAppBack
import com.ghifarix.simulasi_uang.model.GeneratePdf
import com.ghifarix.simulasi_uang.screens.kpr.detail.DetailLoanItemText

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
                Column(
                    modifier = Modifier
                        .padding(it)
                        .horizontalScroll(state = rememberScrollState())
                ) {
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
                                Spacer(modifier = Modifier.width(10.dp))
                                DetailLoanItemText(text = item.investmentIncrease, isLast = isLast)
                                Spacer(modifier = Modifier.width(10.dp))
                                DetailLoanItemText(text = item.tax, isLast = isLast)
                                Spacer(modifier = Modifier.width(10.dp))
                                DetailLoanItemText(text = item.investment, isLast = isLast)
                                Spacer(modifier = Modifier.width(10.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}