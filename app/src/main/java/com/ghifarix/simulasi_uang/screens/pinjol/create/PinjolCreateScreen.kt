@file:OptIn(ExperimentalMaterial3Api::class)

package com.ghifarix.simulasi_uang.screens.pinjol.create

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
import androidx.compose.material.icons.filled.Percent
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
import androidx.compose.ui.unit.dp
import com.ghifarix.simulasi_uang.components.BaseLoan
import com.ghifarix.simulasi_uang.components.SubmitButton
import com.ghifarix.simulasi_uang.components.TextFieldCustom
import com.ghifarix.simulasi_uang.components.TextFieldDisplay
import com.ghifarix.simulasi_uang.screens.pinjol.model.PinjolType

@Composable
fun PinjolCreateScreen(
    pinjolCreateViewModel: PinjolCreateViewModel,
    onClickHamburger: () -> Unit = {},
    onNavigateToDetail: () -> Unit = {}
) {

    val state = pinjolCreateViewModel.state.collectAsState().value
    val dpAmount = pinjolCreateViewModel.serviceCost.collectAsState().value
    val pinjolType = pinjolCreateViewModel.pinjolType.collectAsState().value

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Pinjol") },
                navigationIcon = {
                    IconButton(onClick = { onClickHamburger() }) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = "hamburger menu pinjol"
                        )
                    }
                })
        }
    ) { padding ->
        Column(modifier = Modifier.padding(paddingValues = padding)) {
            BaseLoan(onTextChanged = pinjolCreateViewModel::updateBaseLoan)
            Row {
                val modifier = Modifier
                    .padding(all = 8.dp)
                TextFieldDisplay(
                    modifier = modifier.weight(0.5f),
                    label = "Rupiah",
                    text = dpAmount,
                    readOnly = true,
                    icon = Icons.Default.Money
                )
                TextFieldCustom(
                    modifier = modifier.weight(0.5f),
                    label = "Biaya Langganan",
                    maxInterest = 15.0,
                    onTextChanged = pinjolCreateViewModel::updateServiceCostInterest
                )
            }
            PinjolType(onClickType = pinjolCreateViewModel::updatePinjolType)
            Row {
                TextFieldCustom(
                    modifier = Modifier
                        .padding(all = 8.dp)
                        .weight(0.3f)
                        .fillMaxWidth(),
                    label = "Lama Pinjaman",
                    icon = Icons.Default.CalendarToday,
                    onTextChanged = pinjolCreateViewModel::updateLoanTime
                )
                if (pinjolType == PinjolType.Harian) {
                    TextFieldCustom(
                        modifier = Modifier
                            .padding(all = 8.dp)
                            .weight(0.7f)
                            .fillMaxWidth(),
                        label = "Bunga Harian",
                        icon = Icons.Default.Percent,
                        maxInterest = 0.4,
                        onTextChanged = pinjolCreateViewModel::updateInterest
                    )
                } else {
                    TextFieldCustom(
                        modifier = Modifier
                            .padding(all = 8.dp)
                            .weight(0.7f)
                            .fillMaxWidth(),
                        label = "Bunga Bulanan",
                        icon = Icons.Default.Percent,
                        maxInterest = 9.0,
                        onTextChanged = pinjolCreateViewModel::updateInterest
                    )
                }
            }
            SubmitButton(
                onClick = pinjolCreateViewModel::submit,
                text = "Hitung"
            )
        }
    }
}

@Composable
private fun PinjolType(
    initialValue: PinjolType = PinjolType.Harian,
    onClickType: (PinjolType) -> Unit = {}
) {

    val state = remember { mutableStateOf(initialValue) }
    Row(
        modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        OutlinedButton(
            onClick = {
                state.value = PinjolType.Harian
                onClickType(state.value)
            }, modifier = Modifier
                .padding(8.dp)
                .weight(1f),
            colors = ButtonDefaults.buttonColors(
                containerColor = getColor(type = PinjolType.Harian, state)
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = PinjolType.Harian.name,
            )
        }
        Spacer(modifier = Modifier.width(2.dp))
        OutlinedButton(
            onClick = {
                state.value = PinjolType.Bulanan
                onClickType(state.value)
            }, modifier = Modifier
                .padding(8.dp)
                .weight(1f),
            colors = ButtonDefaults.buttonColors(
                containerColor = getColor(type = PinjolType.Bulanan, state),
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = PinjolType.Bulanan.name,
            )
        }
        Spacer(modifier = Modifier.width(2.dp))

    }
}

@Composable
private fun getColor(type: PinjolType, state: State<PinjolType>): Color {
    return if (state.value == type) {
        MaterialTheme.colorScheme.primary
    } else Color.White
}

