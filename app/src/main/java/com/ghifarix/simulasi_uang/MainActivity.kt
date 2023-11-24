package com.ghifarix.simulasi_uang

import android.annotation.SuppressLint
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.ghifarix.simulasi_uang.screens.kpr.detail.kprDetailNav
import com.ghifarix.simulasi_uang.screens.kpr.input.KPR_INPUT_ROUTE
import com.ghifarix.simulasi_uang.screens.kpr.input.kprInputNav
import com.ghifarix.simulasi_uang.screens.pinjol.create.PINJOL_CREATE_ROUTE
import com.ghifarix.simulasi_uang.screens.pinjol.create.pinjolCreateNav
import com.ghifarix.simulasi_uang.screens.pinjol.detail.pinjolDetailNav
import com.ghifarix.simulasi_uang.theme.DarkColorPalette
import com.ghifarix.simulasi_uang.theme.LightColorPalette
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import com.google.firebase.crashlytics.FirebaseCrashlytics
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseCrashlytics.getInstance().sendUnsentReports()
        MobileAds.initialize(this) {

        }
        MobileAds.setRequestConfiguration(
            RequestConfiguration.Builder()
                .setTestDeviceIds(listOf("f508ccf1-11f9-418a-b550-1a873e393473")).build()
        )
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
                                    "Simulation Uang",
                                    modifier = Modifier.padding(16.dp),
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            Divider()
                            NavigationDrawerItem(
                                label = { Text(text = "KPR") },
                                selected = false,
                                onClick = {
                                    scope.launch {
                                        drawerState.close()
                                    }
                                    navController.navigate(KPR_INPUT_ROUTE)
                                }
                            )
                            NavigationDrawerItem(
                                label = { Text(text = "Pinjol") },
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
                    NavHost(navController = navController, startDestination = KPR_INPUT_ROUTE) {
                        kprInputNav(navController = navController, drawerState = drawerState)
                        kprDetailNav(navController = navController)
                        pinjolCreateNav(navController = navController, drawerState = drawerState)
                        pinjolDetailNav(navController = navController)
                    }
                }
            }
        }
    }
}

