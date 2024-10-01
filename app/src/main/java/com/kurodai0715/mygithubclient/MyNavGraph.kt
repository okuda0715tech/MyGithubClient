package com.kurodai0715.mygithubclient

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.kurodai0715.mygithubclient.login.LoginScreen
import com.kurodai0715.mygithubclient.profile.ProfileScreen

@Composable
fun MyNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = MyDestinations.LOGIN_ROUTE,
    navActions: MyNavigationActions = remember(navController) {
        MyNavigationActions(navController)
    }
) {
    val currentNavBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentNavBackStackEntry?.destination?.route ?: startDestination

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(MyDestinations.LOGIN_ROUTE) {
            LoginScreen(onLogin = { navActions.navigateToProfile() })
        }
        composable(MyDestinations.PROFILE_ROUTE) {
            ProfileScreen()
        }
    }
}