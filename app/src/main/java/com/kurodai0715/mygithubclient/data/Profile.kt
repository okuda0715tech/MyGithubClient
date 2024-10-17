package com.kurodai0715.mygithubclient.data

data class Profile(
    val id: Int,
    val login: String,
    val avatarUrl: String,
    val name: String?,
    val company: String?,
    val location: String?,
    val email: String?,
    val bio: String?,
)
