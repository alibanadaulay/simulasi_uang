@file:OptIn(ExperimentalMaterial3Api::class)

package com.ghifarix.simulasi_uang.screens.kpr.input

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Percent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.DecimalFormat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KprInputScreen(kprInputViewModel: KprInputViewModel, onClickHamburger: () -> Unit = {}) {
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
            DpAndYearsLoan(kprInputViewModel::updateDp, kprInputViewModel::updateYears)
            BaseRate(kprInputViewModel::updateRate)
            OutlinedButton(
                modifier = Modifier.fillMaxWidth(),
                shape = RectangleShape,
                onClick = { kprInputViewModel.calculate() }) {
                Text(text = "Calculate")
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
            updateBaseLoan(it.text)
            stateValue.value = it.copy(selection = TextRange(it.text.length))
            with(it) {
                if (text.length > 12) {
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
private fun BaseRate(updateRate: (String) -> Unit = {}) {
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
            Text(text = "Bunga (Riba) %", fontSize = 10.sp)
        },
        value = stateValue.value,
        onValueChange = {
            if (it.text.length > 2) {
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
private fun DpAndYearsLoan(updateDp: (String) -> Unit = {}, updateYearLoan: (String) -> Unit = {}) {
    val dpStateValue =
        remember {
            mutableStateOf(
                TextFieldValue(
                    text = "",
                    selection = TextRange.Zero
                )
            )
        }
    var dpState = false
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
    Row {
        OutlinedTextField(
            modifier = Modifier
                .padding(all = 8.dp)
                .weight(1f),
            label = {
                Text(text = "DP")
            },
            value = dpStateValue.value,
            onValueChange = {
                if (it.text.length > 2) {
                    return@OutlinedTextField
                }
                dpState = true
                dpStateValue.value = it.copy(selection = TextRange(it.text.length))
                updateDp(it.text)
            },
            isError = dpStateValue.value.text.length < 5 && dpState,
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
        OutlinedTextField(
            modifier = Modifier
                .padding(all = 8.dp)
                .weight(1f),
            label = {
                Text(text = "Lama Pinjaman (Tahun)", fontSize = 10.sp)
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
}

@Preview
@Composable
fun BaseLoanPreview() {
    BaseLoan()
}