package com.kurodai0715.mygithubclient

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.kurodai0715.mygithubclient.ui.theme.MyGithubClientTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            MyGithubClientTheme {
                MyNavGraph()
            }
        }
    }
}

@Preview
@Composable
private fun MyNavGraphPreview() {
    MyGithubClientTheme {
        MyNavGraph()
    }
}