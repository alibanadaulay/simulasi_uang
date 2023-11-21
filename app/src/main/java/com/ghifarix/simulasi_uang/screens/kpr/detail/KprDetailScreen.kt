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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ghifarix.simulasi_uang.screens.kpr.model.Kpr

private const val TextDp = 140

@Composable
fun KprDetailScreen(kprDetailViewModel: KprDetailViewModel) {
    LaunchedEffect(key1 = true) {
        kprDetailViewModel.getKpr()
    }

    val state = kprDetailViewModel.state.collectAsState().value
    Scaffold(topBar = { TopAppBar(title = { Text(text = "Detail") }) }) { pads ->
        when (state) {
            is KprDetailState.LoadKprDetails -> {
                Column(modifier = Modifier.padding(pads)) {
                    ShowDetail(kpr = state.kpr)
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
            .padding(all = 8.dp)
    ) {
        Row (
            Modifier
                .padding(bottom = 8.dp)
                .background(Color.Blue)){
            Text(
                text = "Bulan",
                modifier = Modifier.width(48.dp),
                textAlign = TextAlign.Center
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
                Column {
                    Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                        Text(
                            text = "${item.month}",
                            modifier = Modifier.width(48.dp),
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            modifier = Modifier.width(TextDp.dp),
                            text = item.interest,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            modifier = Modifier.width(TextDp.dp),
                            text = item.capital,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            modifier = Modifier.width(TextDp.dp),
                            text = item.installments,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            modifier = Modifier.width(TextDp.dp),
                            text = item.remainingLoan,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        })
        Row {
            Text(
                text = "Total",
                modifier = Modifier.width(48.dp),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.width(10.dp))
            TitleText(text = kpr.totalInterest)
            Spacer(modifier = Modifier.width(10.dp))
            TitleText(text = kpr.totalCapital)
            Spacer(modifier = Modifier.width(10.dp))
            TitleText(text = kpr.totalInstallments)
            Spacer(modifier = Modifier.width(10.dp))
            TitleText(text = "--")
        }
    }
}

@Composable
private fun ShowDetail(kpr: Kpr) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 8.dp),
        colors = CardDefaults.outlinedCardColors(),
        border = BorderStroke(1.dp, color = Color.Gray)
    ) {
        DetailLoanText(
            title = "Jenis Angsuran", text = kpr
                .installmentsType
        )
        DetailLoanText(title = "Pinjaman Pokok", text = "Rp ${kpr.totalLoan}")
        DetailLoanText(
            title = "Lama Pinjaman (Tahun)", text = kpr
                .years.toString()
        )
        DetailLoanText(
            title = "Bunga (Riba)", text = kpr.interest.toString()
        )
    }
}

@Composable
private fun DetailLoanText(title: String, text: String) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp, top=2.dp, bottom = 2.dp)
    ) {
        Text(text = title, fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.weight(1f))
        Text(text = text, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun TitleText(text: String) {
    Text(
        text = text,
        modifier = Modifier.width(TextDp.dp),
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center
    )
}