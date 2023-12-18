package com.ghifarix.simulasi_uang.screens.investment.detail

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val INVESTMENT_DETAIL_ROUTE = "investment_detail_route"
fun NavGraphBuilder.investmentDetailNav(navController: NavController) {
    composable(INVESTMENT_DETAIL_ROUTE) {
        InvestmentDetailScreen(onBack = {
            navController.popBackStack()
        })
    }
}