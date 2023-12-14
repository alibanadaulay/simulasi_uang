package com.ghifarix.simulasi_uang.screens.investment.create

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ghifarix.simulasi_uang.R
import com.ghifarix.simulasi_uang.components.BannerAdsView
import com.ghifarix.simulasi_uang.components.BaseLoan
import com.ghifarix.simulasi_uang.components.SubmitButton
import com.ghifarix.simulasi_uang.components.TextFieldCustom
import com.ghifarix.simulasi_uang.components.TopAppBarHamburgerMenu

@Composable
fun InvestmentCreateScreen(
    vm: InvestmentCreateViewModel = hiltViewModel(),
    onSubmit: () -> Unit = {},
    onClickHamburger: () -> Unit = {}
) {

    val state = vm.state.collectAsState().value
    LaunchedEffect(key1 = true) {
        if (state is InvestmentCreateState.Submit) {
            onSubmit()
            vm.reset()
        }
    }

    Scaffold(topBar = {
        TopAppBarHamburgerMenu(
            title = stringResource(id = R.string.investment),
            onClickHamburger = onClickHamburger
        )
    }) { padd ->
        Column(modifier = Modifier.padding(padd)) {
            BaseLoan(onTextChanged = vm::updateBaseLoan)
            Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                val modifier = Modifier
                    .padding(all = 8.dp)
                    .weight(1f)
                TextFieldCustom(
                    modifier = modifier,
                    icon = Icons.Default.CalendarToday,
                    label = "Lama Investasi (Year)",
                    onTextChanged = vm::updateYears
                )
                TextFieldCustom(
                    modifier = modifier,
                    label = "Pertambahan %",
                    onTextChanged = vm::updateInvestment
                )
            }
            SubmitButton(
                onClick = vm::calculate,
                text = "Hitung"
            )
            Spacer(modifier = Modifier.weight(1f))
            BannerAdsView()
        }
    }
}