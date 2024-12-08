package com.kurodai0715.mygithubclient.data

data class UserRepo(
    val id: Int,
    val name: String,
    val ownerLogin: String,
    val ownerAvatarUrl: String,
    val description: String?,
    val stargazersCount: Int,
    val language: String?,
)
