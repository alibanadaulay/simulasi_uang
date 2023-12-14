package com.ghifarix.simulasi_uang.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Percent
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.DecimalFormat


@Composable
fun BaseLoan(
    onTextChanged: (String) -> Unit = {},
    maxLength: Int = 14
) {
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
            .padding(all = 7.dp)
            .fillMaxWidth(),
        label = {
            Text(text = "Jumlah Pinjaman")
        },
        value = stateValue.value,
        onValueChange = {
            state = true
            with(it) {
                if (text.length > maxLength) {
                    return@OutlinedTextField
                }
                if (text.isEmpty()) {
                    stateValue.value = copy(
                        text = "",
                        selection = TextRange.Zero
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
                onTextChanged(amountFormat)
            }
        },
        isError = stateValue.value.text.length < 4 && state,
        singleLine = true,
        maxLines = 0,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
        placeholder = {
            Text(text = "")
        })
}

@Composable
fun TextFieldCustom(
    modifier: Modifier = Modifier,
    maxLength: Int = 5,
    state: MutableState<TextFieldValue> =
        remember {
            mutableStateOf(
                TextFieldValue(
                    text = "",
                    selection = TextRange.Zero
                )
            )
        },
    readOnly: Boolean = false,
    onTextChanged: (String) -> Unit = {},
    icon: ImageVector = Icons.Default.Percent,
    label: String = "",
    maxInterest: Double? = null
) {
    var isOnchange = false
    OutlinedTextField(
        modifier = modifier,
        label = {
            Text(text = label, fontSize = 10.sp, maxLines = 1)
        },
        readOnly = readOnly,
        value = state.value,
        onValueChange = {
            var currentText = it.text
            if (currentText.length > maxLength) {
                return@OutlinedTextField
            }
            maxInterest?.let { max ->
                if (currentText.isNotEmpty() && max < currentText.toDouble()) {
                    currentText = maxInterest.toString()
                }
            }
            isOnchange = true
            state.value = it.copy(
                selection = TextRange(currentText.length),
                text = currentText
            )
            onTextChanged(currentText)
        },
        isError = state.value.text.length < 5 && isOnchange,
        singleLine = true,
        maxLines = 1,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        placeholder = {
            Text(text = "0.0")
        },
        trailingIcon = {
            Icon(
                imageVector = icon,
                contentDescription = ""
            )
        })
}

@Composable
fun TextFieldDisplay(
    modifier: Modifier = Modifier,
    text: String = "",
    readOnly: Boolean = false,
    icon: ImageVector = Icons.Default.Percent,
    label: String = "",
) {
    OutlinedTextField(
        modifier = modifier,
        label = {
            Text(text = label, fontSize = 10.sp, maxLines = 1)
        },
        readOnly = readOnly,
        value = text,
        onValueChange = {},
        singleLine = true,
        maxLines = 1,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        placeholder = {
            Text(text = "0.0")
        },
        trailingIcon = {
            Icon(
                imageVector = icon,
                contentDescription = ""
            )
        })
}


