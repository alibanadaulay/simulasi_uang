package com.ghifarix.simulasi_uang.screens.kpr.detail

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable


const val KPR_DETAIL_ROUTE="kpr-detail"
fun NavGraphBuilder.kprDetailNav(navController: NavController) {
    composable(KPR_DETAIL_ROUTE){
       KprDetailScreen(hiltViewModel())
    }
}