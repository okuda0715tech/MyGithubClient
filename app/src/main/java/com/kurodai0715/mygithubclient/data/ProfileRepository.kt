package com.kurodai0715.mygithubclient.data

import kotlinx.coroutines.flow.Flow

interface ProfileRepository {

    fun getPatStream(): Flow<String>

    fun updatePat(pat: String)

    fun getRetainPat(): Flow<Boolean>

    fun updateRetainPat(checked: Boolean)

    suspend fun loadProfile()

    fun fetchProfileStream(): Flow<Profile?>

}