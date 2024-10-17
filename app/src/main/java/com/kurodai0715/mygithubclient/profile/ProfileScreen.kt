package com.kurodai0715.mygithubclient.profile

import android.inputmethodservice.Keyboard
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
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

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun ProfileContent(
    profile: Profile?,
    modifier: Modifier = Modifier
) {
    // TODO ログアウト機能を実装する。ログアウト時には、ローカルのデータを基本的に全て削除する。
    Column(modifier = modifier) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            GlideImage(
                model = profile?.avatarUrl,
                contentDescription = "user icon",
                loading = placeholder(ColorPainter(Color.LightGray)),
                contentScale = ContentScale.Crop,
                modifier = modifier
                    .padding(8.dp)
                    .size(60.dp)
                    .clip(CircleShape)
            )
            Column(verticalArrangement = Arrangement.Center) {
                Text(text = profile?.name ?: "", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Text(text = profile?.login ?: "", fontSize = 14.sp)
            }
        }
        Text(text = "id = " + profile?.id)
        Text(text = "login = " + profile?.login)
        Text(text = "e-mail = " + profile?.email)
    }
}

@Preview(apiLevel = 34)
@Composable
private fun ScreenPreview() {
    ProfileContent(
        profile = Profile(
            id = 1234567,
            login = "Ichiro1234",
            avatarUrl = "",
            name = "Suzuki Ichiro",
            email = "abcd@gmail.com",
        ),
    )
}