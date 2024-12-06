package com.kurodai0715.mygithubclient.data

import kotlinx.coroutines.flow.Flow

interface ProfileRepository {

    suspend fun loadProfile(): Int

    fun fetchProfileStream(): Flow<Profile?>

    suspend fun loadUserRepos(): Int
}