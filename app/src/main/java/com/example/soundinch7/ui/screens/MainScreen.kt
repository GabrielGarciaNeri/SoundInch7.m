package com.example.soundinch7.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.soundinch7.ui.UserSessionViewModel
import com.example.soundinch7.ui.components.BottomNavigationBar
import com.example.soundinch7.ui.navigation.SoundInRoutes

@Composable
fun MainScreen(
    sessionViewModel: UserSessionViewModel,
    onLogout: () -> Unit
){
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                currentRoute = currentRoute,
                onNavigate = { route ->
                    navController.navigate(route){
                        popUpTo (navController.graph.startDestinationId){
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState =true
                    }
                }
            )
        } // end of bottom bar
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = SoundInRoutes.LIBRARY,
            modifier = Modifier.padding(paddingValues)
        ) // end of nav host arguments
        {
            composable(SoundInRoutes.LIBRARY) {LibraryScreen()}
            composable(SoundInRoutes.SEARCH) {SearchScreen()}
            composable (SoundInRoutes.PROFILE) {ProfileScreen(
                sessionViewModel = sessionViewModel,
                onLogout = onLogout
            )}
        }   // end of nav host
    } // end Scaffold
}
