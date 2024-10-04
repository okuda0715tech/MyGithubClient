package com.kurodai0715.mygithubclient.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

interface ProfileRepository {

    fun getPatStream(): Flow<String>

    fun updatePat(pat: String)

    suspend fun getProfile(savePat: Boolean)

    fun getProfileStream(): Flow<Profile?>

}