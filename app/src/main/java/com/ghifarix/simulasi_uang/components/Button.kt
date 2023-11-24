package com.ghifarix.simulasi_uang.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp

class Button {
}

@Composable
fun SubmitButton(
    modifier: Modifier = Modifier
        .padding(8.dp)
        .fillMaxWidth(),
    onClick: () -> Unit = {},
    text: String = "Submit"
) {
    OutlinedButton(
        modifier = modifier,
        shape = RectangleShape,
        onClick = { onClick() }) {
        Text(text = text)
    }
}