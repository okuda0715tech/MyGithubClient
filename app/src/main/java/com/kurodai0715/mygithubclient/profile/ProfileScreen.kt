package com.kurodai0715.mygithubclient.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kurodai0715.mygithubclient.data.Profile

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel()
) {

    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->


        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

        ProfileContent(
            profile = uiState.profile,
            modifier = Modifier.padding(paddingValues)
        )
    }

}

@Composable
private fun ProfileContent(
    profile: Profile?,
    modifier: Modifier = Modifier
) {
    // TODO ログアウト機能を実装する。ログアウト時には、ローカルのデータを基本的に全て削除する。
    Column(modifier = modifier) {
        Text(text = "id = " + profile?.id)
        Text(text = "login = " + profile?.login)
        Text(text = "e-mail = " + profile?.email)
    }
}

@Preview
@Composable
private fun ScreenPreview() {
    ProfileContent(
        profile = Profile(
            id = 1234567,
            login = "login name?",
            email = "abcd@gmail.com"
        ),
    )
}