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
import com.ghifarix.simulasi_uang.components.DetailLoanItemText
import com.ghifarix.simulasi_uang.components.DetailLoanText
import com.ghifarix.simulasi_uang.components.TopAppBack
import com.ghifarix.simulasi_uang.model.GeneratePdf
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
        TopAppBack(
            context = context,
            onBack = onBack,
            title = stringResource(
                id = R.string.pinjol_detail
            ),
        )
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
            .padding(top = 10.dp)
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
                text = stringResource(id = if (pinjol.installmentsType == PinjolType.Harian) R.string.day else R.string.month),
                modifier = Modifier.width(64.dp),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.width(10.dp))
            TitleText(text = stringResource(id = R.string.interest))
            Spacer(modifier = Modifier.width(10.dp))
            TitleText(text = stringResource(id = R.string.capital))
            Spacer(modifier = Modifier.width(10.dp))
            TitleText(text = stringResource(id = R.string.installment))
            Spacer(modifier = Modifier.width(10.dp))
            TitleText(text = stringResource(id = R.string.remaining_debt))
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
        DetailLoanText(title = "Pinjaman", text = pinjol.totalLoan)
        DetailLoanText(title = "Biaya Layanan (${pinjol.dp}%)", text = pinjol.dpAmount)
        DetailLoanText(title = "Pinjaman Dibayar", text = pinjol.loanToPay)
        DetailLoanText(
            title = "Bunga (Riba)", text = "${pinjol.interest}%"
        )
        DetailLoanText(
            title = "Lama Pinjaman (${pinjol.installmentsType.name})",
            text = pinjol.loanTime.toString()
        )
        DetailLoanText(
            title = "Pertambahan (${pinjol.interestAtPercentage}%)",
            text =
            pinjol.interestAmount
        )
    }
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