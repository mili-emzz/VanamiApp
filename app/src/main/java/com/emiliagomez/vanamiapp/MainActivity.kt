package com.emiliagomez.vanamiapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.emiliagomez.vanamiapp.navigation.AppNavHost
import com.emiliagomez.vanamiapp.navigation.BottomNav
import com.emiliagomez.vanamiapp.navigation.MainScreen
import com.emiliagomez.vanamiapp.presentation.viewmodels.LoginViewModel
import com.emiliagomez.vanamiapp.presentation.viewmodels.RecordViewModel
import com.emiliagomez.vanamiapp.ui.theme.BackgroundColor
import com.emiliagomez.vanamiapp.ui.theme.VanamiAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VanamiAppTheme {
                val loginViewModel: LoginViewModel by viewModels()
                MainScreen(loginViewModel = loginViewModel)
            }
        }
    }
}

@Composable
fun MainScreen(loginViewModel: LoginViewModel,  recordViewModel: RecordViewModel) {
    val navController = rememberNavController()

    Scaffold(
        containerColor = BackgroundColor,
        bottomBar = {
            BottomNav(navController = navController)
        }
    ) { innerPadding ->
        AppNavHost(
            navController = navController,
            loginViewModel = loginViewModel,
            recordViewModel = recordViewModel,
            modifier = Modifier.padding(innerPadding),
            innerPadding = innerPadding
        )
    }
}