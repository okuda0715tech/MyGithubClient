package com.kurodai0715.mygithubclient.data.source.network

interface NetworkDataSource {

    suspend fun loadProfile(auth: String): UserApiResponse

}