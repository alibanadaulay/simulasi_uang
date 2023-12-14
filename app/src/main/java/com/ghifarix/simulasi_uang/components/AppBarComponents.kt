@file:OptIn(ExperimentalMaterial3Api::class)

package com.ghifarix.simulasi_uang.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable

@Composable
fun TopAppBarHamburgerMenu(
    title: String = "this title",
    onClickHamburger: () -> Unit = {}
) {
    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            IconButton(onClick = { onClickHamburger() }) {
                Icon(imageVector = Icons.Filled.Menu, contentDescription = "hamburger menu")
            }
        })
}