package com.ghifarix.simulasi_uang.screens.pinjol.create

import androidx.compose.material3.DrawerState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ghifarix.simulasi_uang.screens.pinjol.detail.PINJOL_DETAIL_ROUTE
import kotlinx.coroutines.launch

const val PINJOL_CREATE_ROUTE = "pinjol-create"
fun NavGraphBuilder.pinjolCreateNav(navController: NavController, drawerState: DrawerState) {
    composable(PINJOL_CREATE_ROUTE) {
        val scope = rememberCoroutineScope()
        PinjolCreateScreen(
            hiltViewModel(), onClickHamburger = {
                scope.launch {
                    drawerState.open()
                }
            },
            onNavigateToDetail = {
                scope.launch {
                    navController.navigate(PINJOL_DETAIL_ROUTE)
                }
            })
    }
}