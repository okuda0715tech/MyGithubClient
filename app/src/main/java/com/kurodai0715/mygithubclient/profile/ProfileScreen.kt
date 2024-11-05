package com.kurodai0715.mygithubclient.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
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
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.kurodai0715.mygithubclient.ui.theme.MyGithubClientTheme
import com.kurodai0715.mygithubclient.R
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
            modifier = Modifier
                .padding(paddingValues)
                .consumeWindowInsets(paddingValues)
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
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surfaceContainerHigh)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
                .padding(12.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                GlideImage(
                    model = profile?.avatarUrl,
                    contentDescription = "user icon",
                    loading = placeholder(ColorPainter(Color.LightGray)),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape),
                    requestBuilderTransform = {
                        it.skipMemoryCache(true) // メモリキャッシュをスキップ
                            .diskCacheStrategy(DiskCacheStrategy.NONE) // ディスクキャッシュを無効化
                    }
                )
                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(start = 12.dp)
                ) {
                    Text(text = profile?.name ?: "", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                    Text(
                        text = profile?.login ?: "",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.outline
                    )
                }
            }
            Text(text = profile?.bio ?: "")
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.outline_business_24),
                    contentDescription = stringResource(
                        id = R.string.company_icon_description
                    ),
                    modifier = Modifier.size(18.dp),
                    tint = MaterialTheme.colorScheme.outline,
                )
                Text(text = profile?.company ?: "")
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    painter = painterResource(id = R.drawable.outline_location_on_24),
                    contentDescription = stringResource(
                        id = R.string.location_icon_description
                    ),
                    modifier = Modifier.size(18.dp),
                    tint = MaterialTheme.colorScheme.outline,
                )
                Text(
                    text = profile?.location ?: "",
                    color = MaterialTheme.colorScheme.outline,
                )
            }
        }
        HorizontalDivider()
        Spacer(modifier = Modifier.height(16.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
                .padding(12.dp)
        ) {
            Row {
                Icon(
                    painter = painterResource(id = R.drawable.outline_location_on_24),
                    contentDescription = stringResource(
                        id = R.string.location_icon_description
                    ),
                    modifier = Modifier.size(18.dp),
                )
                Text(text = "人気")
            }
        }
        HorizontalDivider()
    }
}

@Preview(apiLevel = 34)
@Composable
private fun ScreenPreview() {
    MyGithubClientTheme {
        ProfileContent(
            profile = Profile(
                id = 1234567,
                login = "Ichiro1234",
                avatarUrl = "",
                name = "Suzuki Ichiro",
                company = "Company Name",
                location = "Location",
                email = "abcd@gmail.com",
                bio = "Android Engineer"
            ),
        )
    }
}