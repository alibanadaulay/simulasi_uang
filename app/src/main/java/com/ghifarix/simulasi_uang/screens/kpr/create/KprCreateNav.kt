package com.ghifarix.simulasi_uang.screens.kpr.create

import androidx.compose.material3.DrawerState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ghifarix.simulasi_uang.screens.kpr.detail.KPR_DETAIL_ROUTE
import kotlinx.coroutines.launch

const val KPR_INPUT_ROUTE = "kpr-input"

fun NavGraphBuilder.kprInputNav(navController: NavController, drawerState: DrawerState) {
    composable(KPR_INPUT_ROUTE) {
        val scope = rememberCoroutineScope()
        KprInputScreen(hiltViewModel(), onClickHamburger = {
            scope.launch {
                drawerState.open()
            }
        },
            onNavigateToDetail = {
                scope.launch {
                    navController.navigate(KPR_DETAIL_ROUTE)
                }
            })
    }
}