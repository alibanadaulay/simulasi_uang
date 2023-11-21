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
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ghifarix.simulasi_uang.screens.kpr.model.KprType
import java.text.DecimalFormat

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
            BaseLoan(kprInputViewModel::updateBaseLoan)
            Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                val modifier = Modifier
                    .padding(all = 8.dp)
                    .weight(1f)
                Years(updateYearLoan = kprInputViewModel::updateYears, modifier = modifier)
                BaseRate(updateRate = kprInputViewModel::updateRate, modifier = modifier)
            }
            Row {
                val modifier = Modifier
                    .padding(all = 8.dp)
                DpAmount(modifier = modifier.weight(0.7f), dpAmount = dpAmount)
                Dp(modifier = modifier.weight(0.3f), updateDp = kprInputViewModel::updateDp)
            }
            KprType(onClickType = kprInputViewModel::updateKprType)
            OutlinedButton(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                shape = RectangleShape,
                onClick = { kprInputViewModel.calculate() }) {
                Text(text = "Hitung")
            }
        }
    }
}

@Composable
private fun BaseLoan(updateBaseLoan: (String) -> Unit = {}) {
    val stateValue =
        remember {
            mutableStateOf(
                TextFieldValue(
                    text = "",
                    selection = TextRange.Zero
                )
            )
        }
    var state = false
    OutlinedTextField(
        modifier = Modifier
            .padding(all = 8.dp)
            .fillMaxWidth(),
        label = {
            Text(text = "Jumlah Pinjaman")
        },
        value = stateValue.value,
        onValueChange = {
            state = true
            with(it) {
                if (text.length > 15) {
                    return@OutlinedTextField
                }
                if (text.isEmpty()) {
                    stateValue.value = copy(
                        text = "0",
                        selection = TextRange(1)
                    )
                    return@OutlinedTextField
                }
                if (text.last() == '.') {
                    stateValue.value = this
                    return@OutlinedTextField
                }
                val a = text.find { char ->
                    (char == '.')
                }
                if (a != null) {
                    stateValue.value = this
                    return@OutlinedTextField
                }
                val tempText = text.replace(",", "")
                val format = DecimalFormat("#,###.###")
                val amountFormat = format.format(tempText.toDouble())
                stateValue.value = it.copy(
                    text = amountFormat,
                    selection = TextRange(amountFormat.length)
                )
                updateBaseLoan(amountFormat)
            }
        },
        isError = stateValue.value.text.length < 5 && state,
        singleLine = true,
        maxLines = 1,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        placeholder = {
            Text(text = "")
        })
}

@Composable
private fun Dp(modifier: Modifier, updateDp: (String) -> Unit = {}) {
    val stateValue =
        remember {
            mutableStateOf(
                TextFieldValue(
                    text = "0.0",
                    selection = TextRange(3)
                )
            )
        }
    var state = false
    OutlinedTextField(
        modifier = modifier,
        label = {
            Text(text = "DP", maxLines = 1)
        },
        value = stateValue.value,
        onValueChange = {
            if (it.text.length > 5) {
                return@OutlinedTextField
            }
            state = true
            stateValue.value = it.copy(selection = TextRange(it.text.length))
            updateDp(it.text)
        },
        isError = stateValue.value.text.length < 5 && state,
        singleLine = true,
        maxLines = 1,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        placeholder = {
            Text(text = "")
        },
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.Percent,
                contentDescription = "percent"
            )
        })
}


@Composable
private fun DpAmount(modifier: Modifier, dpAmount: String) {
    OutlinedTextField(
        modifier = modifier,
        readOnly = true,
        label = {
            Text(text = "Rupiah")
        },
        value = dpAmount,
        onValueChange = {

        },
        singleLine = true,
        maxLines = 1,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        placeholder = {
            Text(text = "")
        },
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.Money,
                contentDescription = "percent"
            )
        })
}

@Composable
private fun BaseRate(modifier: Modifier, updateRate: (String) -> Unit = {}) {
    val stateValue =
        remember {
            mutableStateOf(
                TextFieldValue(
                    text = "",
                    selection = TextRange.Zero
                )
            )
        }
    var state = false
    OutlinedTextField(
        modifier = modifier,
        label = {
            Text(text = "Bunga (Riba)")
        },
        value = stateValue.value,
        onValueChange = {
            if (it.text.length > 5) {
                return@OutlinedTextField
            }
            state = true
            stateValue.value = it.copy(selection = TextRange(it.text.length))
            updateRate(it.text)
        },
        isError = stateValue.value.text.length < 5 && state,
        singleLine = true,
        maxLines = 1,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        placeholder = {
            Text(text = "")
        },
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.Percent,
                contentDescription = "percent"
            )
        })
}

@Composable
private fun Years(updateYearLoan: (String) -> Unit = {}, modifier: Modifier) {
    val yearsStateValue =
        remember {
            mutableStateOf(
                TextFieldValue(
                    text = "",
                    selection = TextRange.Zero
                )
            )
        }
    var yearsState = false
    OutlinedTextField(
        modifier = modifier,
        label = {
            Text(text = "Lama Pinjaman (Tahun)", fontSize = 10.sp, maxLines = 1)
        },
        value = yearsStateValue.value,
        onValueChange = {
            if (it.text.length > 2) {
                return@OutlinedTextField
            }
            yearsState = true
            yearsStateValue.value = it.copy(selection = TextRange(it.text.length))
            updateYearLoan(it.text)
        },
        isError = yearsStateValue.value.text.length < 5 && yearsState,
        singleLine = true,
        maxLines = 1,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        placeholder = {
            Text(text = "")
        },
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.CalendarToday,
                contentDescription = ""
            )
        })
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