package com.ghifarix.simulasi_uang.screens.kpr.input

import androidx.compose.material3.DrawerState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val KPR_INPUT_ROUTE="kpr-input"

fun NavGraphBuilder.kprInputNav(navController: NavController, drawerState: DrawerState) {
    composable(KPR_INPUT_ROUTE){
        KprInputScreen()
    }
}