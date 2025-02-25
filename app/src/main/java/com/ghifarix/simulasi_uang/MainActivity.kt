package com.ghifarix.simulasi_uang

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.ghifarix.simulasi_uang.screens.investment.create.INVESTMENT_CREATE_ROUTE
import com.ghifarix.simulasi_uang.screens.investment.create.investmentCreateNav
import com.ghifarix.simulasi_uang.screens.investment.detail.investmentDetailNav
import com.ghifarix.simulasi_uang.screens.kpr.create.KPR_INPUT_ROUTE
import com.ghifarix.simulasi_uang.screens.kpr.create.kprInputNav
import com.ghifarix.simulasi_uang.screens.kpr.detail.kprDetailNav
import com.ghifarix.simulasi_uang.screens.pinjol.create.PINJOL_CREATE_ROUTE
import com.ghifarix.simulasi_uang.screens.pinjol.create.pinjolCreateNav
import com.ghifarix.simulasi_uang.screens.pinjol.detail.pinjolDetailNav
import com.ghifarix.simulasi_uang.theme.DarkColorPalette
import com.ghifarix.simulasi_uang.theme.LightColorPalette
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val colors = if (isSystemInDarkTheme()) {
                DarkColorPalette
            } else {
                LightColorPalette
            }
            MaterialTheme(colorScheme = colors) {
                val navController = rememberNavController()
                val scope = rememberCoroutineScope()
                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                BackHandler {
                    scope.launch {
                        if (drawerState.isOpen) {
                            drawerState.close()
                        } else {
                            navController.popBackStack()
                        }
                    }
                }
                ModalNavigationDrawer(
                    drawerState = drawerState,
                    gesturesEnabled = false,
                    drawerContent = {
                        ModalDrawerSheet {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                IconButton(onClick = {
                                    scope.launch {
                                        drawerState.close()
                                    }
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.ArrowBack,
                                        contentDescription = "close Navigation Drawer"
                                    )
                                }
                                Text(
                                    text = stringResource(id = R.string.app_name),
                                    modifier = Modifier.padding(16.dp),
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            Divider()
                            NavigationDrawerItem(
                                label = { Text(text = stringResource(id = R.string.investment)) },
                                selected = false,
                                onClick = {
                                    scope.launch {
                                        drawerState.close()
                                    }
                                    navController.navigate(INVESTMENT_CREATE_ROUTE)
                                }
                            )
                            Divider(
                                thickness = 2.dp,
                            )
                            NavigationDrawerItem(
                                label = { Text(text = stringResource(id = R.string.mortgage)) },
                                selected = false,
                                onClick = {
                                    scope.launch {
                                        drawerState.close()
                                    }
                                    navController.navigate(KPR_INPUT_ROUTE)
                                }
                            )
                            NavigationDrawerItem(
                                label = { Text(text = stringResource(id = R.string.pinjol)) },
                                selected = false,
                                onClick = {
                                    scope.launch {
                                        drawerState.close()
                                    }
                                    navController.navigate(PINJOL_CREATE_ROUTE)
                                }
                            )

                        }
                    }) {
                    NavHost(
                        navController = navController,
                        startDestination = INVESTMENT_CREATE_ROUTE
                    ) {
                        kprInputNav(navController = navController, drawerState = drawerState)
                        kprDetailNav(navController = navController)
                        pinjolCreateNav(navController = navController, drawerState = drawerState)
                        pinjolDetailNav(navController = navController)
                        investmentCreateNav(
                            navController = navController,
                            drawerState = drawerState
                        )
                        investmentDetailNav(navController = navController)
                    }
                }
            }
        }
    }
}

