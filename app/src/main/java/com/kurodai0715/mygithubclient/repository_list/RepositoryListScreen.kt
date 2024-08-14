package com.kurodai0715.mygithubclient.repository_list

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun RepositoryListScreen() {
    Text(text = "repository list")
}

@Preview
@Composable
private fun ScreenPreview(){
    RepositoryListScreen()
}