package com.kurodai0715.mygithubclient.data

import android.util.Log
import com.kurodai0715.mygithubclient.data.source.local.ProfileDao
import com.kurodai0715.mygithubclient.data.source.network.NetworkDataSource
import com.kurodai0715.mygithubclient.data.source.toExternal
import com.kurodai0715.mygithubclient.data.source.toLocal
import com.kurodai0715.mygithubclient.di.ApplicationScope
import com.kurodai0715.mygithubclient.di.DefaultDispatcher
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
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher,
    @ApplicationScope private val scope: CoroutineScope,
) : ProfileRepository {

    /**
     * サーバーからデータをロードして、ローカルに保存する。
     *
     * ローカルのデータは保存する前にクリアする。
     */
    override suspend fun refresh() {
        Log.v(TAG, "refresh is started.")
        withContext(dispatcher) {
            val remoteProfile = networkDataSource.loadProfile()
            Log.d(TAG, "remoteProfile = $remoteProfile")

            // DB へ登録する。
            localDataSource.upsert(remoteProfile.toLocal())
        }
    }

    override fun getProfileStream(): Flow<Profile?> {
        return localDataSource.observe().map {
            Log.v(TAG, "localDataSource is changed.")
            it?.toExternal()
        }
    }
}