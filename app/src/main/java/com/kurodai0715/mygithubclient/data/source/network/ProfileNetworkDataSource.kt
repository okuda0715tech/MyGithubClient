package com.kurodai0715.mygithubclient.data.source.network

import com.kurodai0715.mygithubclient.data.source.network.github.GithubApi
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject

class ProfileNetworkDataSource @Inject constructor() : NetworkDataSource {

    private val accessMutex = Mutex()

    override suspend fun loadProfile(auth: String): NetworkProfile = accessMutex.withLock {
        // todo try{}catch{} が必要では？
        GithubApi.retrofitService.getProfile(auth = auth)
    }

}