package com.emiliagomez.vanamiapp.navigation

import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.emiliagomez.vanamiapp.R
import com.emiliagomez.vanamiapp.components.NavManager.routes.AuthRoutes
import com.emiliagomez.vanamiapp.components.NavManager.routes.Destination
import com.emiliagomez.vanamiapp.presentation.viewmodels.LoginViewModel
import com.emiliagomez.vanamiapp.presentation.viewmodels.RecordViewModel
import com.emiliagomez.vanamiapp.presentation.views.*
import com.emiliagomez.vanamiapp.ui.theme.BackgroundColor
import com.emiliagomez.vanamiapp.ui.theme.MainColor
import com.emiliagomez.vanamiapp.ui.theme.NavGray

@Composable
fun MainScreen(
    loginViewModel: LoginViewModel
) {
    val navController = rememberNavController()
    val recordViewModel: RecordViewModel = viewModel()

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
            innerPadding = innerPadding
        )
    }
}

// Rutas adicionales para el flujo de registro
object RecordRoutes {
    const val RECORD_EMOTION = "record_emotion"
    const val RECORD_HABIT = "record_habit"
}

@Composable
fun AppNavHost(
    navController: NavHostController,
    loginViewModel: LoginViewModel,
    recordViewModel: RecordViewModel,
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = Destination.CALENDAR.route,
        modifier = modifier
    ) {
        // CALENDARIO
        composable(Destination.CALENDAR.route) {
            CalendarView(
                recordViewModel = recordViewModel,
                onDayClick = { selectedDate ->
                    Log.d("CalendarView", "Día seleccionado: $selectedDate")

                    if (loginViewModel.isUserAuthenticated()) {
                        // Navegar al registro de emoción para ese día
                        navController.navigate(RecordRoutes.RECORD_EMOTION)
                    } else {
                        navController.navigate(AuthRoutes.REGISTER)
                    }
                },
                imageResId = R.drawable.info_content,
                onDiaryClick = {
                    if (loginViewModel.isUserAuthenticated()) {
                        // Ir al registro del día actual
                        navController.navigate(RecordRoutes.RECORD_EMOTION)
                    } else {
                        navController.navigate(AuthRoutes.REGISTER)
                    }
                }
            )
        }

        // REGISTRO DE EMOCIÓN
        composable(RecordRoutes.RECORD_EMOTION) {
            if (loginViewModel.isUserAuthenticated()) {
                RecordEmotionView(
                    recordViewModel = recordViewModel,
                    onContinueClick = {
                        // Continuar al registro de hábitos
                        navController.navigate(RecordRoutes.RECORD_HABIT)
                    }
                )
            } else {
                LaunchedEffect(Unit) {
                    navController.navigate(AuthRoutes.REGISTER) {
                        popUpTo(Destination.CALENDAR.route) { inclusive = false }
                    }
                }
            }
        }

        // REGISTRO DE HÁBITOS
        composable(RecordRoutes.RECORD_HABIT) {
            if (loginViewModel.isUserAuthenticated()) {
                RecordHabitView(
                    recordViewModel = recordViewModel,
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onSaveSuccess = {
                        // Volver al calendario después de guardar
                        navController.navigate(Destination.CALENDAR.route) {
                            popUpTo(Destination.CALENDAR.route) { inclusive = true }
                        }
                    }
                )
            } else {
                LaunchedEffect(Unit) {
                    navController.navigate(AuthRoutes.REGISTER) {
                        popUpTo(Destination.CALENDAR.route) { inclusive = false }
                    }
                }
            }
        }

        // TAB ADD (para acceso rápido)
        composable(Destination.ADD.route) {
            if (loginViewModel.isUserAuthenticated()) {
                LaunchedEffect(Unit) {
                    navController.navigate(RecordRoutes.RECORD_EMOTION) {
                        popUpTo(Destination.CALENDAR.route) { inclusive = false }
                    }
                }
            } else {
                LaunchedEffect(Unit) {
                    navController.navigate(AuthRoutes.REGISTER)
                }
            }
        }

        composable(Destination.FAVORITES.route) {
            if (loginViewModel.isUserAuthenticated()) {
                // falta implementarla bien jeje, no le piques pq explota
                CalendarView(
                    recordViewModel = recordViewModel,
                    onDayClick = {},
                    imageResId = R.drawable.info_content,
                    onDiaryClick = {}
                )
            } else {
                LaunchedEffect(Unit) {
                    navController.navigate(AuthRoutes.REGISTER)
                }
            }
        }
        composable(Destination.PROFILE.route) {
            val isAuthenticated = loginViewModel.isUserAuthenticated()

            if (isAuthenticated) {
                ProfileAuthenticatedView(
                    loginViewModel = loginViewModel,
                    onLogout = {
                        loginViewModel.logout()
                        navController.navigate(Destination.PROFILE.route) {
                            popUpTo(Destination.PROFILE.route) { inclusive = true }
                        }
                    },
                    onNavigateToFav = {
                        navController.navigate(Destination.FAVORITES.route)
                    }
                )
            } else {
                RegisterView(
                    loginViewModel = loginViewModel,
                    onNavigateToLogin = {
                        navController.navigate(AuthRoutes.LOGIN)
                    },
                    onRegisterSuccess = {

                        recordViewModel.loadUserRecords()
                        navController.navigate(Destination.PROFILE.route) {
                            popUpTo(Destination.PROFILE.route) { inclusive = true }
                        }
                    }
                )
            }
        }

        composable(AuthRoutes.LOGIN) {
            LoginView(
                loginViewModel = loginViewModel,
                onNavigateToRegister = {
                    navController.popBackStack()
                },
                onLoginSuccess = {
                    recordViewModel.loadUserRecords()
                    navController.navigate(Destination.PROFILE.route) {
                        popUpTo(AuthRoutes.LOGIN) { inclusive = true }
                    }
                }
            )
        }

        composable(AuthRoutes.REGISTER) {
            RegisterView(
                loginViewModel = loginViewModel,
                onNavigateToLogin = {
                    navController.navigate(AuthRoutes.LOGIN)
                },
                onRegisterSuccess = {
                    recordViewModel.loadUserRecords()
                    navController.navigate(Destination.PROFILE.route) {
                        popUpTo(AuthRoutes.REGISTER) { inclusive = true }
                    }
                }
            )
        }
    }
}

@Composable
fun BottomNav(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val hideBottomNav = currentRoute in listOf(
        RecordRoutes.RECORD_EMOTION,
        RecordRoutes.RECORD_HABIT
    )

    if (!hideBottomNav) {
        NavigationBar(
            containerColor = Color.White
        ) {
            Destination.entries.forEach { destination ->
                val isSelected = when {
                    currentRoute in listOf(AuthRoutes.LOGIN, AuthRoutes.REGISTER) && destination == Destination.PROFILE -> true
                    currentRoute == AuthRoutes.PROFILE_AUTHENTICATED && destination == Destination.PROFILE -> true
                    else -> currentRoute == destination.route
                }

                NavigationBarItem(
                    selected = isSelected,
                    onClick = {
                        if (currentRoute != destination.route) {
                            navController.navigate(destination.route) {
                                popUpTo(Destination.CALENDAR.route) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = destination.icon,
                            contentDescription = destination.route,
                            tint = if (isSelected) MainColor else NavGray,
                            modifier = Modifier.size(34.dp)
                        )
                    }
                )
            }
        }
    }
}