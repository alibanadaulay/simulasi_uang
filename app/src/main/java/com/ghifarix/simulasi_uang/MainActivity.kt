package com.ghifarix.simulasi_uang

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.ghifarix.simulasi_uang.screens.kpr.detail.kprDetailNav
import com.ghifarix.simulasi_uang.screens.kpr.input.KPR_INPUT_ROUTE
import com.ghifarix.simulasi_uang.screens.kpr.input.kprInputNav
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
            ModalNavigationDrawer(
                drawerState = drawerState,
                drawerContent = {
                    ModalDrawerSheet {
                        Text("Drawer title", modifier = Modifier.padding(16.dp))
                        Divider()
                        NavigationDrawerItem(
                            label = { Text(text = "KPR") },
                            selected = false,
                            onClick = { /*TODO*/ }
                        )
                        NavigationDrawerItem(
                            label = { Text(text = "Leasing") },
                            selected = false,
                            onClick = { /*TODO*/ }
                        )
                    }
                }) {
                NavHost(navController = navController, startDestination = KPR_INPUT_ROUTE){
                    kprInputNav(navController = navController, drawerState = drawerState)
                    kprDetailNav(navController = navController )
                }
            }
        }
    }

}

