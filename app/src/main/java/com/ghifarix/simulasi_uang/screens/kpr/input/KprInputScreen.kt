@file:OptIn(ExperimentalMaterial3Api::class)

package com.ghifarix.simulasi_uang.screens.kpr.input

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Money
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ghifarix.simulasi_uang.components.BaseLoan
import com.ghifarix.simulasi_uang.components.SubmitButton
import com.ghifarix.simulasi_uang.components.TextFieldCustom
import com.ghifarix.simulasi_uang.components.TextFieldDisplay
import com.ghifarix.simulasi_uang.screens.kpr.model.KprType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KprInputScreen(
    kprInputViewModel: KprInputViewModel,
    onClickHamburger: () -> Unit = {},
    onNavigateToDetail: () -> Unit = {}
) {


    val state = kprInputViewModel.state.collectAsState().value
    val dpAmount = kprInputViewModel.dpAmount.collectAsState().value

    if (state is KprInputState.Submit) {
        onNavigateToDetail()
        kprInputViewModel.resetState()
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "KPR") },
                navigationIcon = {
                    IconButton(onClick = { onClickHamburger() }) {
                        Icon(imageVector = Icons.Filled.Menu, contentDescription = "hamburger menu")
                    }
                })
        }
    ) { padding ->
        Column(modifier = Modifier.padding(paddingValues = padding)) {
            BaseLoan(onTextChanged = kprInputViewModel::updateBaseLoan)
            Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                val modifier = Modifier
                    .padding(all = 8.dp)
                    .weight(1f)
                TextFieldCustom(
                    modifier = modifier,
                    icon = Icons.Default.CalendarToday,
                    label = "Lama Pinjaman (Tahun)",
                    onTextChanged = kprInputViewModel::updateYears
                )
                TextFieldCustom(
                    modifier = modifier,
                    label = "Bunga (Riba)",
                    onTextChanged = kprInputViewModel::updateRate
                )
            }
            Row {
                val modifier = Modifier
                    .padding(all = 8.dp)
                TextFieldDisplay(
                    modifier = modifier.weight(0.7f),
                    label = "Rupiah",
                    text = dpAmount,
                    readOnly = true,
                    icon = Icons.Default.Money
                )
                TextFieldCustom(
                    modifier = modifier.weight(0.3f),
                    label = "DP",
                    onTextChanged = kprInputViewModel::updateDp
                )
            }
            KprType(onClickType = kprInputViewModel::updateKprType)
            SubmitButton(
                onClick = kprInputViewModel::calculate,
                text = "Hitung"
            )
        }
    }
}

@Composable
private fun KprType(
    initialValue: KprType = KprType.ANUITAS,
    onClickType: (KprType) -> Unit = {}
) {

    val state = remember { mutableStateOf(initialValue) }
    Row(
        modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        OutlinedButton(
            onClick = {
                state.value = KprType.ANUITAS
                onClickType(state.value)
            }, modifier = Modifier
                .padding(8.dp)
                .weight(1f),
            colors = ButtonDefaults.buttonColors(
                containerColor = getColor(type = KprType.ANUITAS, state)
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = KprType.ANUITAS.name,
            )
        }
        Spacer(modifier = Modifier.width(2.dp))
        OutlinedButton(
            onClick = {
                state.value = KprType.FLAT
                onClickType(state.value)
            }, modifier = Modifier
                .padding(8.dp)
                .weight(1f),
            colors = ButtonDefaults.buttonColors(
                containerColor = getColor(type = KprType.FLAT, state),
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = KprType.FLAT.name,
            )
        }
        Spacer(modifier = Modifier.width(2.dp))
        OutlinedButton(
            onClick = {
                state.value = KprType.EFEKTIF
                onClickType(state.value)
            }, modifier = Modifier
                .padding(8.dp)
                .weight(1f),
            colors = ButtonDefaults.buttonColors(
                containerColor = getColor(type = KprType.EFEKTIF, state)
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = KprType.EFEKTIF.name,
            )
        }
        Spacer(modifier = Modifier.width(2.dp))
    }
}

@Composable
private fun getColor(type: KprType, state: State<KprType>): Color {
    return if (state.value == type) {
        MaterialTheme.colorScheme.primary
    } else Color.White
}


@Preview
@Composable
fun BaseLoanPreview() {
    BaseLoan()
}