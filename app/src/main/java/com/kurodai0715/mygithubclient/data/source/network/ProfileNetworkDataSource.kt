package com.kurodai0715.mygithubclient.data.source.network

import android.util.Log
import com.kurodai0715.mygithubclient.data.source.network.github.GithubApi
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject

private const val TAG = "ProfileNetworkDataSource"

// TODO クラス名を UserNetworkDataSource に変更したい。(エンドポイントが user で始まる API をこのクラスにまとめる予定)
class ProfileNetworkDataSource @Inject constructor() : NetworkDataSource {

    private val accessMutex = Mutex()

    override suspend fun loadProfile(auth: String): UserApiResponse = accessMutex.withLock {
        try {
            val response = GithubApi.retrofitService.getProfile(auth = auth)
            Log.d(TAG, "response = $response")
            if (response.isSuccessful) {
                UserApiResponse.Success(response)
            } else {
                UserApiResponse.ContentsError(response)
            }
        } catch (e: Exception) {
            UserApiResponse.NoResponseError(e)
        }
    }

}