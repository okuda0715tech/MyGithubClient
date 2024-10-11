package com.kurodai0715.mygithubclient.data

import android.util.Log
import com.kurodai0715.mygithubclient.data.source.local.ProfileDao
import com.kurodai0715.mygithubclient.data.source.network.NetworkDataSource
import com.kurodai0715.mygithubclient.data.source.network.UserApiResponse
import com.kurodai0715.mygithubclient.data.source.toExternal
import com.kurodai0715.mygithubclient.data.source.toLocal
import com.kurodai0715.mygithubclient.di.ApplicationScope
import com.kurodai0715.mygithubclient.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

private const val TAG = "DefaultProfileRepository"

@Singleton
class DefaultProfileRepository @Inject constructor(
    private val networkDataSource: NetworkDataSource,
    private val localDataSource: ProfileDao,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    @ApplicationScope private val scope: CoroutineScope,
) : ProfileRepository {

    /**
     * サーバーからデータをロードして、ローカルに保存する。
     *
     * ローカルのデータは保存する前にクリアする。
     *
     * @return サーバーからのレスポンスコード
     */
    override suspend fun loadProfile(pat: String): Int {
        Log.v(TAG, "refresh is started.")

        return withContext(dispatcher) {
            val patHeader = "Bearer $pat"
            val userApiResponse = networkDataSource.loadProfile(patHeader)
            Log.d(TAG, "remoteProfile = $userApiResponse")

            when (userApiResponse) {
                is UserApiResponse.Success -> {
                    Log.d(TAG, "user API response body = ${userApiResponse.response.body()}")
                    // DB へ登録する。
                    localDataSource.upsert(userApiResponse.response.body()!!.toLocal())
                    return@withContext userApiResponse.response.code()
                }

                is UserApiResponse.ContentsError -> {
                    Log.d(TAG, "HttpException = ${userApiResponse.response}")
                    return@withContext userApiResponse.response!!.code()
                }

                is UserApiResponse.NoResponseError -> {
                    // todo
                    return@withContext 999
                }
            }
        }
    }

    /**
     * ローカルからデータを取得する.
     */
    override fun fetchProfileStream(): Flow<Profile?> {
        return localDataSource.observe().map {
            Log.v(TAG, "localDataSource is changed.")
            it?.toExternal()
        }
    }
}