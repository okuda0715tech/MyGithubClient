package com.kurodai0715.mygithubclient.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
            onRefresh = { viewModel.refresh() },
            modifier = Modifier.padding(paddingValues)
        )

        uiState.userMessage?.let { userMessage ->
            val snackbarText = stringResource(userMessage)
            LaunchedEffect(viewModel, userMessage, snackbarText) {
                snackbarHostState.showSnackbar(message = snackbarText)
                // メッセージをクリア
                viewModel.snackbarMessageShown()
            }
        }
    }

}

@Composable
private fun ProfileContent(
    profile: Profile?,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column {
        Button(onClick = { onRefresh() }) {
            Text(text = "リクエストを実行")
        }

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
        onRefresh = {}
    )
}