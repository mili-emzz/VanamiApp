package com.emiliagomez.vanamiapp.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.AddCircleOutline
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.emiliagomez.vanamiapp.ui.theme.MainColor
import com.emiliagomez.vanamiapp.ui.theme.NavGray
import com.guru.fontawesomecomposelib.FaIconType
import com.guru.fontawesomecomposelib.FaIcons

enum class Destination(
    val route: String,
    val icon: ImageVector,
) {
    HOME("home", Icons.Outlined.Home),
    CALENDAR("calendar", Icons.Filled.CalendarMonth),
    ADD("add", Icons.Outlined.AddCircleOutline),
    FAVORITES("favorites", Icons.Outlined.FavoriteBorder),
    PROFILE("profile", Icons.Outlined.Person)

}

//cambiar cuando las rutas esten listas
@Composable
fun AppNavHost() {
//    navController: NavHostController,
//    startDestination: Destination.HOME.route,
    val modifier = Modifier
//    NavHost(
//        navController,
//        startDestination = startDestination.route
//        modifier = Modifier
//    ) {
//            // Cada destino corresponde a una pantalla
//        composable(Destination.HOME.route) {
//            HomeScreen()
//        }
//        composable(Destination.CALENDAR.route) {
//            CalendarScreen()
//        }
//        composable(Destination.ADD.route) {
//            AddScreen()
//        }
//        composable(Destination.FAVORITES.route) {
//            FavoritesScreen()
//        }
//        composable(Destination.PROFILE.route) {
//            ProfileScreen()
//        }}
}

@Composable
fun BottomNav(
//    navController: NavHostController
) {
    // Observa el destino actual
    val navController = rememberNavController()
    val startDestination = Destination.HOME
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(
        windowInsets = NavigationBarDefaults.windowInsets,
        containerColor = Color.White
        ) {
        Destination.entries.forEachIndexed { index, destination ->
            val isSelect = currentRoute == destination.route
            NavigationBarItem(
                onClick = {
                    navController.navigate(destination.route)
                                },
                icon = {
                    Icon(
                        destination.icon,
                        contentDescription = null,
                        tint = if (isSelect) MainColor else NavGray,
                        modifier = Modifier
                            .size(34.dp)
                    )
                },
                selected = isSelect
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavPreview() {
    BottomNav()
}