package com.kurodai0715.mygithubclient.data

import kotlinx.coroutines.flow.Flow

interface ProfileRepository {

    suspend fun loadProfile(pat: String = ""): Int

    fun fetchProfileStream(): Flow<Profile?>

}