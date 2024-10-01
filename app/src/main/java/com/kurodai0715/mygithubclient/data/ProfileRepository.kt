package com.kurodai0715.mygithubclient.data

import kotlinx.coroutines.flow.Flow

interface ProfileRepository {

    suspend fun refresh()

    fun getProfileStream(): Flow<Profile?>

}