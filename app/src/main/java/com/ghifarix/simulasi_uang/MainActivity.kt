package com.ghifarix.simulasi_uang

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
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
import com.ghifarix.simulasi_uang.theme.DarkColorPalette
import com.ghifarix.simulasi_uang.theme.LightColorPalette
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
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
                                label = { Text(text = "Leasing") },
                                selected = false,
                                onClick = { /*TODO*/ }
                            )
                        }
                    }) {
                    NavHost(navController = navController, startDestination = KPR_INPUT_ROUTE) {
                        kprInputNav(navController = navController, drawerState = drawerState)
                        kprDetailNav(navController = navController)
                    }
                }
            }
        }
    }
}

