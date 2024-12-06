package com.kurodai0715.mygithubclient.data.source

import com.kurodai0715.mygithubclient.data.Profile
import com.kurodai0715.mygithubclient.data.UserRepo
import com.kurodai0715.mygithubclient.data.source.local.LocalProfile
import com.kurodai0715.mygithubclient.data.source.local.LocalUserRepo
import com.kurodai0715.mygithubclient.data.source.network.NetworkProfile
import com.kurodai0715.mygithubclient.data.source.network.NetworkUserRepo

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

fun NetworkUserRepo.toLocal() = LocalUserRepo(
    id = id,
    name = name,
)

fun LocalUserRepo.toExternal() = UserRepo(
    id = id,
    name = name,
)