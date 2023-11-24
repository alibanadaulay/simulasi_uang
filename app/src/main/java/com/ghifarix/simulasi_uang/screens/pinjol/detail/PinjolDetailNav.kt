package com.ghifarix.simulasi_uang.screens.pinjol.detail

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable


const val PINJOL_DETAIL_ROUTE = "pinjol-detail"
fun NavGraphBuilder.pinjolDetailNav(navController: NavController) {
    composable(PINJOL_DETAIL_ROUTE) {
        PinjolDetailScreen(hiltViewModel(), onBack = {
            navController.popBackStack()
        })
    }
}