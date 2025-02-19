@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.ghifarix.simulasi_uang.components

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.ghifarix.simulasi_uang.SingletonModel
import com.ghifarix.simulasi_uang.model.GeneratePdf

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

@Composable
fun TopAppBack(
    context: Context,
    title: String = "title",
    onBack: () -> Unit = {},
    generatePdf: GeneratePdf = GeneratePdf.KPR
) {
    TopAppBar(title = {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = {
                onBack()
            }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = title
                )
            }
            Text(text = title)
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = {
                SingletonModel.getInstance()
                    .generatePdf(context = context, generatePdf = GeneratePdf.KPR)
            }) {
                Icon(
                    imageVector = Icons.Default.Download,
                    contentDescription = title
                )
            }
        }
    })
}