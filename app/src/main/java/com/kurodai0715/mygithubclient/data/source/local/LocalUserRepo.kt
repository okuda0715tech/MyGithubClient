package com.kurodai0715.mygithubclient.data.source.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "user_repos"
)
data class LocalUserRepo(
    @PrimaryKey
    val id: Int,
    val name: String,
    val ownerLogin: String,
    val ownerAvatarUrl: String,
    val description: String?,
    val stargazersCount: Int,
    val language: String?,
)