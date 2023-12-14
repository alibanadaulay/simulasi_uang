package com.ghifarix.simulasi_uang.screens.investment.create

import androidx.compose.material3.DrawerState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ghifarix.simulasi_uang.screens.investment.detail.INVESTMENT_DETAIL_ROUTE
import kotlinx.coroutines.launch


const val INVESTMENT_CREATE_ROUTE = "investasi-create"
fun NavGraphBuilder.investmentCreateNav(navController: NavController, drawerState: DrawerState) {
    composable(INVESTMENT_CREATE_ROUTE) {
        val scope = rememberCoroutineScope()
        InvestmentCreateScreen(onSubmit = { navController.navigate(INVESTMENT_DETAIL_ROUTE) },
            onClickHamburger = {
                scope.launch {
                    drawerState.open()
                }
            })
    }
}