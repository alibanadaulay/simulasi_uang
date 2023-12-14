package com.ghifarix.simulasi_uang.screens.investment.create

import androidx.compose.material3.DrawerState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable


const val INVESTMENT_CREATE_ROUTE = "investasi-create"
fun NavGraphBuilder.investmentCreateNav(navController: NavController, drawerState: DrawerState) {
    composable(INVESTMENT_CREATE_ROUTE) {
        InvestmentCreateScreen()
    }
}