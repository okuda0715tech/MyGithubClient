package com.kurodai0715.mygithubclient.data.source.network

import android.util.Log
import com.kurodai0715.mygithubclient.data.source.network.github.GithubApi
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import retrofit2.HttpException
import javax.inject.Inject

const val TAG = "ProfileNetworkDataSource"

class ProfileNetworkDataSource @Inject constructor() : NetworkDataSource {

    private val accessMutex = Mutex()

    override suspend fun loadProfile(auth: String): UserApiResponse = accessMutex.withLock {
        try {
            UserApiResponse.Success(GithubApi.retrofitService.getProfile(auth = auth))
        } catch (e: HttpException) {
            // TODO エラーが返ってきた場合は、画面にメッセージを表示する等の対応を行う。
            UserApiResponse.Error(e)
        }
    }

}