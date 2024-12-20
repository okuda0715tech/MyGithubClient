package com.kurodai0715.mygithubclient.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Shapes
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.asFloatState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.kurodai0715.mygithubclient.ui.theme.MyGithubClientTheme
import com.kurodai0715.mygithubclient.R
import com.kurodai0715.mygithubclient.data.Profile
import com.kurodai0715.mygithubclient.data.UserRepo
import com.kurodai0715.mygithubclient.ui.component.SurfaceButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel()
) {

    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TopAppBar(title = {}, actions = {
                Icon(
                    Icons.Outlined.Share,
                    contentDescription = stringResource(
                        id = R.string.share_icon_description
                    ),
                    modifier = Modifier
                        .size(48.dp)
                        .padding(12.dp),
                    tint = MaterialTheme.colorScheme.primary,
                )
                Icon(
                    Icons.Outlined.Settings,
                    contentDescription = stringResource(
                        id = R.string.setting_icon_description
                    ),
                    modifier = Modifier
                        .size(48.dp)
                        .padding(12.dp),
                    tint = MaterialTheme.colorScheme.primary,
                )
            })
        }
    ) { paddingValues ->


        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

        ProfileContent(
            profile = uiState.profile,
            userRepos = uiState.userRepos,
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
    userRepos: List<UserRepo>?,
    modifier: Modifier = Modifier
) {
    val appColorScheme = MaterialTheme.colorScheme
    val appTypography = MaterialTheme.typography
    val appShapes = MaterialTheme.shapes
    val iconSize = 16.dp

    // TODO ログアウト機能を実装する。ログアウト時には、ローカルのデータを基本的に全て削除する。
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(appColorScheme.surfaceContainerHigh)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(appColorScheme.surface)
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                GlideImage(
                    model = profile?.avatarUrl,
                    contentDescription = "user icon",
                    loading = placeholder(ColorPainter(Color.LightGray)),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(56.dp)
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
                    Text(
                        text = profile?.name ?: "",
                        style = appTypography.titleLarge,
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        text = profile?.login ?: "",
                        style = appTypography.labelLarge,
                        color = appColorScheme.outline,
                        fontWeight = FontWeight.Normal,
                    )
                }
            }
            SurfaceButton(
                onClick = { /*TODO*/ },
                shape = appShapes.extraSmall,
                border = BorderStroke(width = 0.1.dp, color = appColorScheme.outlineVariant),
                modifier = Modifier.fillMaxWidth(),
                color = appColorScheme.surfaceContainer,
                contentColor = appColorScheme.onSurfaceVariant,
            ) {
                Row(
                    modifier = Modifier.padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.outline_emoji_emotions_24),
                        contentDescription = stringResource(
                            id = R.string.your_state_icon_description
                        ),
                        modifier = Modifier.size(iconSize),
                        tint = appColorScheme.outline,
                    )
                    Text(
                        text = stringResource(id = R.string.set_state),
                        style = appTypography.labelLarge,
                        modifier = Modifier.weight(1f),
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.outline_edit_24),
                        contentDescription = stringResource(
                            id = R.string.editable_icon_description
                        ),
                        modifier = Modifier.size(iconSize),
                        tint = appColorScheme.outline,
                    )
                }
            }
            Text(
                text = profile?.bio ?: "",
                style = appTypography.labelLarge,
                fontWeight = FontWeight.Normal,
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.outline_business_24),
                    contentDescription = stringResource(
                        id = R.string.company_icon_description
                    ),
                    modifier = Modifier.size(iconSize),
                    tint = appColorScheme.outline,
                )
                Text(
                    text = profile?.company ?: "",
                    style = appTypography.labelLarge,
                    fontWeight = FontWeight.Normal,
                )
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    painter = painterResource(id = R.drawable.outline_location_on_24),
                    contentDescription = stringResource(
                        id = R.string.location_icon_description
                    ),
                    modifier = Modifier.size(iconSize),
                    tint = appColorScheme.outline,
                )
                Text(
                    text = profile?.location ?: "",
                    color = appColorScheme.outline,
                    style = appTypography.labelMedium,
                    fontWeight = FontWeight.Normal,
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_people_outline_24),
                    contentDescription = stringResource(
                        id = R.string.following_icon_description
                    ),
                    modifier = Modifier.size(iconSize),
                    tint = appColorScheme.outline,
                )
                Text(
                    text = profile?.following.toString(),
                    style = appTypography.labelLarge,
                    fontWeight = FontWeight.Medium,
                )
                Text(
                    text = stringResource(id = R.string.following_suffix),
                    style = appTypography.labelLarge,
                    fontWeight = FontWeight.Normal,
                    color = appColorScheme.outline,
                )
            }
        }
        HorizontalDivider()
        Spacer(modifier = Modifier.height(16.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(appColorScheme.surface)
                .padding(start = 12.dp, top = 12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.round_star_outline_24),
                    contentDescription = stringResource(
                        id = R.string.popular_icon_description
                    ),
                    modifier = Modifier.size(16.dp),
                    tint = appColorScheme.onSurfaceVariant,
                )
                Text(
                    text = stringResource(id = R.string.popular),
                    style = appTypography.labelLarge,
                    fontWeight = FontWeight.Medium,
                )
            }
        }
        val screenWidth = LocalContext.current.resources.displayMetrics.widthPixels
        val density = LocalDensity.current.density

        val contentWidth =
            remember {
                // 画面幅の 7 割りの幅
                mutableFloatStateOf((screenWidth / density) * 0.7f).asFloatState()
            }

        LazyRow(
            contentPadding = PaddingValues(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .background(appColorScheme.surface),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (userRepos != null) {
                items(userRepos) { userRepo ->
                    Repository(
                        userRepo = userRepo,
                        contentWidth = contentWidth.floatValue,
                        appColorScheme = appColorScheme,
                        appTypography = appTypography,
                        appShapes = appShapes,
                    )
                }
            }
        }
        HorizontalDivider()
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(appColorScheme.surface)
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.outline_inbox_24),
                    contentDescription = stringResource(
                        id = R.string.popular_icon_description
                    ),
                    modifier = Modifier
                        .clip(appShapes.extraSmall)
                        .background(colorResource(id = R.color.gray))
                        .padding(6.dp)
                        .size(16.dp),
                    tint = colorResource(id = R.color.white),
                )
                Text(
                    text = stringResource(id = R.string.repository),
                    style = appTypography.labelLarge,
                    fontWeight = FontWeight.Normal,
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.outline_business_24),
                    contentDescription = stringResource(
                        id = R.string.organization_icon_description
                    ),
                    modifier = Modifier
                        .clip(appShapes.extraSmall)
                        .background(colorResource(id = R.color.orange))
                        .padding(6.dp)
                        .size(16.dp),
                    tint = colorResource(id = R.color.white),
                )
                Text(
                    text = stringResource(id = R.string.organization),
                    style = appTypography.labelLarge,
                    fontWeight = FontWeight.Normal,
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.round_star_outline_24),
                    contentDescription = stringResource(
                        id = R.string.starred_icon_description
                    ),
                    modifier = Modifier
                        .clip(appShapes.extraSmall)
                        .background(colorResource(id = R.color.yellow))
                        .padding(6.dp)
                        .size(16.dp),
                    tint = colorResource(id = R.color.white),
                )
                Text(
                    text = stringResource(id = R.string.starred),
                    style = appTypography.labelLarge,
                    fontWeight = FontWeight.Normal,
                )
            }
        }
        HorizontalDivider()
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun Repository(
    userRepo: UserRepo,
    contentWidth: Float,
    appColorScheme: ColorScheme,
    appTypography: Typography,
    appShapes: Shapes,
) {
    SurfaceButton(
        onClick = { /*TODO*/ },
        shape = appShapes.extraSmall,
        modifier = Modifier
            .border(
                border = BorderStroke(
                    width = 0.1.dp,
                    color = appColorScheme.outlineVariant
                ),
                shape = appShapes.extraSmall
            )
            .width(contentWidth.dp),
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(2.dp),
            modifier = Modifier.padding(12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                GlideImage(
                    model = userRepo.ownerAvatarUrl,
                    contentDescription = "user icon",
                    loading = placeholder(ColorPainter(Color.LightGray)),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape),
                    requestBuilderTransform = {
                        it.skipMemoryCache(true) // メモリキャッシュをスキップ
                            .diskCacheStrategy(DiskCacheStrategy.NONE) // ディスクキャッシュを無効化
                    }
                )

                Text(
                    text = userRepo.ownerLogin,
                    color = appColorScheme.outline,
                    style = appTypography.labelMedium,
                    fontWeight = FontWeight.Normal,
                )
            }

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = userRepo.name,
                maxLines = 1,
                style = appTypography.labelLarge,
                fontWeight = FontWeight.SemiBold,
            )
            Text(
                text = userRepo.description ?: "",
                maxLines = 1,
                style = appTypography.labelLarge,
                fontWeight = FontWeight.Normal
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.round_star_24),
                    contentDescription = stringResource(
                        id = R.string.num_of_starred_icon_description
                    ),
                    modifier = Modifier.size(16.dp),
                    tint = colorResource(id = R.color.yellow),
                )
                Text(
                    text = userRepo.stargazersCount.toString(),
                    color = appColorScheme.outline,
                    style = appTypography.labelLarge,
                    fontWeight = FontWeight.Normal,
                )
                if (userRepo.language != null) {
                    val color = rememberLangColor(userRepo.language)
                    Box(
                        modifier = Modifier
                            .padding(4.dp)
                            .clip(CircleShape)
                            .background(color)
                            .size(8.dp)
                    )
                    Text(
                        text = userRepo.language,
                        color = appColorScheme.outline,
                        style = appTypography.labelLarge,
                        fontWeight = FontWeight.Normal,
                    )
                }
            }
        }
    }
}

@Composable
private fun rememberLangColor(language: String): Color {

    var color by remember {
        mutableStateOf(Color.Gray)
    }

    color = when (language) {
        "Java" -> colorResource(id = R.color.brown)
        "Kotlin" -> colorResource(id = R.color.purple)
        else -> Color.Gray
    }

    return color
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
                bio = "Android Engineer",
                following = 100,
            ),
            userRepos = listOf(
                UserRepo(
                    id = 1,
                    name = "Suzuki Ichiro",
                    ownerLogin = "Ichiro1234",
                    ownerAvatarUrl = "",
                    description = "#リポジトリの説明",
                    stargazersCount = 100,
                    language = "Java"
                ),
                UserRepo(
                    id = 2,
                    name = "Shohei Otani",
                    ownerLogin = "Shohei5678",
                    ownerAvatarUrl = "",
                    description = "#二刀流プレイヤーの苦悩",
                    stargazersCount = 10000,
                    language = "Kotlin"
                )
            ),
        )
    }
}