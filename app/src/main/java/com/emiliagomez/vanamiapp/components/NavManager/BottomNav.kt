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
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.emiliagomez.vanamiapp.R
import com.emiliagomez.vanamiapp.components.NavManager.routes.Destination
import com.emiliagomez.vanamiapp.components.NavManager.routes.AuthRoutes
import com.emiliagomez.vanamiapp.presentation.viewmodels.LoginViewModel
import com.emiliagomez.vanamiapp.presentation.views.*
import com.emiliagomez.vanamiapp.ui.theme.BackgroundColor
import com.emiliagomez.vanamiapp.ui.theme.MainColor
import com.emiliagomez.vanamiapp.ui.theme.NavGray

@Composable
fun MainScreen(
    loginViewModel: LoginViewModel
) {
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
            innerPadding = innerPadding,
        )
    }
}

// navegacion principal
@Composable
fun AppNavHost(
    navController: NavHostController,
    loginViewModel: LoginViewModel,
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = Destination.CALENDAR.route,
        modifier = modifier
    ) {
        composable(Destination.HOME.route) {
            // HomeView()
        }

        composable(Destination.CALENDAR.route) {
            CalendarView(
                onDayClick = { selectedDate ->
                    // Lógica al pulsar día
                    Log.d("CalendarView", "Día seleccionado: $selectedDate")
                },
                imageResId = R.drawable.info_content,
                onDiaryClick = {
                    // Verificar si está autenticado antes de ir al diario
                    if (loginViewModel.isUserAuthenticated()) {
                        // Navegar a la vista de diario
                        // navController.navigate("diary")
                    } else {
                        // Mostrar mensaje o ir a login
                        navController.navigate(AuthRoutes.REGISTER)
                    }
                }
            )
        }

        composable(Destination.ADD.route) {
            // AddView() - Vista para subir emociones
            if (loginViewModel.isUserAuthenticated()) {
                // Mostrar vista de agregar emoción
            } else {
                LaunchedEffect(Unit) {
                    navController.navigate(AuthRoutes.REGISTER)
                }
            }
        }

        composable(Destination.FAVORITES.route) {
            // FavoritesView() - Vista de favoritos
            if (loginViewModel.isUserAuthenticated()) {
                // Mostrar favoritos
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

    val showBottomNav = currentRoute in Destination.entries.map { it.route }

    if (showBottomNav || currentRoute in listOf(AuthRoutes.LOGIN, AuthRoutes.REGISTER)) {
        NavigationBar(
            containerColor = Color.White
        ) {
            Destination.entries.forEach { destination ->
                val isSelected = when {
                    // Si estamos en Login/Register y es el tab de PROFILE, marcarlo como seleccionado
                    currentRoute in listOf(AuthRoutes.LOGIN, AuthRoutes.REGISTER)  && destination == Destination.PROFILE -> true
                    currentRoute == AuthRoutes.PROFILE_AUTHENTICATED && destination == Destination.PROFILE -> true
                    else -> currentRoute == destination.route
                }

                NavigationBarItem(
                    selected = isSelected,
                    onClick = {
                        if (currentRoute != destination.route) {
                            navController.navigate(destination.route) {
                                // Limpiar el stack hasta la ruta de inicio
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
