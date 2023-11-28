@file:OptIn(
    ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class
)

package com.ghifarix.simulasi_uang.screens.pinjol.detail

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Download
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ghifarix.simulasi_uang.SingletonModel
import com.ghifarix.simulasi_uang.extensions.generatePdf
import com.ghifarix.simulasi_uang.screens.pinjol.model.Pinjol
import com.ghifarix.simulasi_uang.screens.pinjol.model.PinjolType

private const val textDp = 150

@Composable
fun PinjolDetailScreen(kprDetailViewModel: PinjolDetailViewModel, onBack: () -> Unit = {}) {
    LaunchedEffect(key1 = true) {
        kprDetailViewModel.getPinjol()
    }

    val state = kprDetailViewModel.state.collectAsState().value
    val context = LocalContext.current
    Scaffold(topBar = {
        TopAppBar(title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { onBack() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "back from kpr detail"
                    )
                }
                Text(text = "Detail Angsuran")
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = {
                    context.generatePdf(
                        pdf = SingletonModel.getInstance().getPdfByPinjol()
                    )
                }) {
                    Icon(
                        imageVector = Icons.Default.Download,
                        contentDescription = "download pinjol"
                    )
                }
            }
        })
    }) { pads ->
        when (state) {
            is PinjolDetailState.LoadPinjolDetails -> {
                Column(modifier = Modifier.padding(pads)) {
                    ShowDetail(pinjol = state.pinjol)
                    ShowList(pinjol = state.pinjol)
                }
            }

            else -> {
                // do nothing
            }
        }

    }
}

@Composable
private fun ShowList(pinjol: Pinjol) {
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
                text = if (pinjol.installmentsType == PinjolType.Harian) "Hari" else "Bulan",
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
            items(pinjol.pinjolItems.size) {
                val item = pinjol.pinjolItems[it]
                val isLast = it == pinjol.pinjolItems.size - 1
                Column(
                    modifier = Modifier
                        .padding(all = 8.dp)
                ) {
                    Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                        Text(
                            text = item.monthOrDay,
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
private fun ShowDetail(pinjol: Pinjol = Pinjol()) {
    Card(
        modifier = Modifier
            .padding(all = 8.dp)
            .fillMaxWidth()
            .shadow(1.dp),
        colors = CardDefaults.outlinedCardColors(),
        border = BorderStroke(1.dp, color = Color.Gray)
    ) {
        DetailLoanText(
            title = "Jenis Angsuran", text = pinjol
                .installmentsType.name
        )
        DetailLoanText(title = "Pinjaman", text = "Rp ${pinjol.totalLoan}")
        DetailLoanText(title = "Biaya Layanan (${pinjol.dp}%)", text = "Rp ${pinjol.dpAmount}")
        DetailLoanText(title = "Pinjaman Dibayar", text = "Rp ${pinjol.loanToPay}")
        DetailLoanText(
            title = "Bunga (Riba)", text = "${pinjol.interest}%"
        )
        DetailLoanText(
            title = "Lama Pinjaman (${pinjol.installmentsType.name})",
            text = pinjol.loanTime.toString()
        )
        DetailLoanText(
            title = "Pertambahan (${pinjol.interestAtPercentage}%)",
            text = "Rp ${pinjol.interestAmount}"
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
private fun DetailLoanItemText(text: String, isLast: Boolean) {
    val fontWeight = if (isLast) FontWeight.Bold else FontWeight.Light
    Text(
        modifier = Modifier.width(textDp.dp),
        text = text,
        fontWeight = fontWeight,
        textAlign = TextAlign.Center,
        fontSize = 16.sp
    )
}

@Composable
private fun TitleText(text: String) {
    Text(
        text = text,
        modifier = Modifier.width(textDp.dp),
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        fontSize = 20.sp
    )
}

@Composable
@Preview
fun ShowDetailPreview() {
    ShowDetail()
}