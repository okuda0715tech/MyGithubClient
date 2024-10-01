package com.kurodai0715.mygithubclient

import androidx.navigation.NavHostController
import com.kurodai0715.mygithubclient.MyScreens.LOGIN_SCREEN
import com.kurodai0715.mygithubclient.MyScreens.PROFILE_SCREEN

/**
 * 画面名.
 */
private object MyScreens {
    const val LOGIN_SCREEN = "login"

    const val PROFILE_SCREEN = "profile"
}

/**
 * ルートは、「画面名 + パラメータ」を示します.
 */
object MyDestinations {

    const val LOGIN_ROUTE = LOGIN_SCREEN

    const val PROFILE_ROUTE = PROFILE_SCREEN

}

class MyNavigationActions(private val navController: NavHostController) {

    fun navigateToProfile() {
        navController.navigate(MyDestinations.PROFILE_ROUTE)
    }

}
