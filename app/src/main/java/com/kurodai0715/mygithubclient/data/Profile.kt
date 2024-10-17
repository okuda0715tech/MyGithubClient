package com.kurodai0715.mygithubclient.data

data class Profile(
    val id: Int,
    val login: String,
    val avatarUrl: String,
    val name: String?,
    val email: String?,
)
