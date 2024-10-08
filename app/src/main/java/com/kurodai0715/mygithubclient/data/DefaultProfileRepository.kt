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

const val TAG = "DefaultProfileRepository"

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
     */
    override suspend fun loadProfile(pat: String) {
        Log.v(TAG, "refresh is started.")

        withContext(dispatcher) {
            val patHeader = "Bearer $pat"
            val remoteProfile = networkDataSource.loadProfile(patHeader)
            Log.d(TAG, "remoteProfile = $remoteProfile")

            if (remoteProfile is UserApiResponse.Success) {
                // DB へ登録する。
                localDataSource.upsert(remoteProfile.profile.toLocal())
            } else if (remoteProfile is UserApiResponse.Error) {
                Log.d(TAG, "HttpException = ${remoteProfile.httpException}")
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