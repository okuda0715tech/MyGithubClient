package com.kurodai0715.mygithubclient.data.source.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "profile"
)
data class LocalProfile(
    @PrimaryKey val id: Int,
    val login: String,
    val email: String?,
)
