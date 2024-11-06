package com.kurodai0715.mygithubclient.data.source

import com.kurodai0715.mygithubclient.data.Profile
import com.kurodai0715.mygithubclient.data.source.local.LocalProfile
import com.kurodai0715.mygithubclient.data.source.network.NetworkProfile

fun NetworkProfile.toLocal() = LocalProfile(
    id = id,
    login = login,
    avatarUrl = avatarUrl,
    name = name,
    company = company,
    location = location,
    email = email,
    bio = bio,
    following = following,
)

fun LocalProfile.toExternal() = Profile(
    id = id,
    login = login,
    avatarUrl = avatarUrl,
    name = name,
    company = company,
    location = location,
    email = email,
    bio = bio,
    following = following,
)