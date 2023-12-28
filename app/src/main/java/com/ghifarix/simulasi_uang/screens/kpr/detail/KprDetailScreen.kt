@file:OptIn(ExperimentalMaterial3Api::class)

package com.ghifarix.simulasi_uang.screens.kpr.detail

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
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ghifarix.simulasi_uang.R
import com.ghifarix.simulasi_uang.components.BannerAdsView
import com.ghifarix.simulasi_uang.components.DetailLoanItemText
import com.ghifarix.simulasi_uang.components.TitleText
import com.ghifarix.simulasi_uang.components.TopAppBack
import com.ghifarix.simulasi_uang.model.GeneratePdf
import com.ghifarix.simulasi_uang.screens.kpr.model.Kpr
import com.google.android.gms.ads.AdSize


private const val TAG = "KprDetailScreen"

@Composable
fun KprDetailScreen(kprDetailViewModel: KprDetailViewModel, onBack: () -> Unit = {}) {
    LaunchedEffect(key1 = true) {
        kprDetailViewModel.getKpr()
    }

    val rewardedAd = kprDetailViewModel.rewardAd.collectAsState()
    val state = kprDetailViewModel.state.collectAsState().value
    val context = LocalContext.current

    Scaffold(topBar = {
        TopAppBack(context = context,
            rewardAds = rewardedAd,
            onBack = onBack,
            initAds = kprDetailViewModel::updateRewardAd,
            title = stringResource(id = R.string.kpr_detail),
            generatePdf = GeneratePdf.KPR,
            showingAds = {
                rewardedAd.value?.show(it) {}
            }
        )
    }) { pads ->
        when (state) {
            is KprDetailState.LoadKprDetails -> {
                Column(modifier = Modifier.padding(pads)) {
                    ShowDetail(kpr = state.kpr)
                    BannerAdsView(adSize = AdSize.LARGE_BANNER)
                    ShowList(kpr = state.kpr)
                }
            }

            else -> {
                // do nothing
            }
        }

    }
}

@Composable
private fun ShowList(kpr: Kpr) {
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
                text = "Bulan",
                modifier = Modifier.width(64.dp),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.width(10.dp))
            TitleText(text = "Bunga")
            Spacer(modifier = Modifier.width(10.dp))
            TitleText(text = "Pokok")
            Spacer(modifier = Modifier.width(10.dp))
            TitleText(text = "Angsuran")
            Spacer(modifier = Modifier.width(10.dp))
            TitleText(text = "Sisa Hutang")
        }
        LazyColumn(content = {
            items(kpr.kprItems.size) {
                val item = kpr.kprItems[it]
                val isLast = it == kpr.kprItems.size - 1
                Column(
                    modifier = Modifier
                        .padding(all = 8.dp)
                ) {
                    Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                        Text(
                            text = item.month,
                            modifier = Modifier.width(64.dp),
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        DetailLoanItemText(text = item.interest, isLast = isLast)
                        Spacer(modifier = Modifier.width(10.dp))
                        DetailLoanItemText(text = item.capital, isLast = isLast)
                        Spacer(modifier = Modifier.width(10.dp))
                        DetailLoanItemText(item.installments, isLast = isLast)
                        Spacer(modifier = Modifier.width(10.dp))
                        DetailLoanItemText(item.remainingLoan, isLast = isLast)
                    }
                }
            }
        })
    }
}

@Composable
private fun ShowDetail(kpr: Kpr = Kpr()) {
    Card(
        modifier = Modifier
            .padding(all = 8.dp)
            .fillMaxWidth()
            .shadow(1.dp),
        colors = CardDefaults.outlinedCardColors(),
        border = BorderStroke(1.dp, color = Color.Gray)
    ) {
        DetailLoanText(
            title = "Jenis Angsuran", text = kpr
                .installmentsType.name
        )
        DetailLoanText(title = "Pinjaman", text = "Rp ${kpr.totalLoan}")
        DetailLoanText(title = "DP (${kpr.dp}%)", text = "Rp ${kpr.dpAmount}")
        DetailLoanText(title = "Pinjaman Dibayar", text = "Rp ${kpr.loanToPay}")
        DetailLoanText(
            title = "Bunga (Riba)", text = "${kpr.interest}%"
        )
        DetailLoanText(
            title = "Lama Pinjaman (Tahun)", text = kpr
                .years.toString()
        )
        DetailLoanText(
            title = "Pertambahan (${kpr.interestAtPercentage}%)", text = "Rp ${kpr.interestAmount}"
        )
    }
}

@Composable
private fun DetailLoanText(title: String, text: String) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp, top = 2.dp, bottom = 2.dp)
    ) {
        Text(text = title, fontWeight = FontWeight.Normal, fontSize = 16.sp)
        Spacer(modifier = Modifier.weight(1f))
        Text(text = text, fontWeight = FontWeight.Bold, fontSize = 18.sp)
    }
}


@Composable
@Preview
fun ShowDetailPreview() {
    ShowDetail()
}