package com.kurodai0715.mygithubclient.data

import android.util.Log
import com.kurodai0715.mygithubclient.data.source.local.GithubPreferences
import com.kurodai0715.mygithubclient.data.source.local.ProfileDao
import com.kurodai0715.mygithubclient.data.source.network.NetworkDataSource
import com.kurodai0715.mygithubclient.data.source.toExternal
import com.kurodai0715.mygithubclient.data.source.toLocal
import com.kurodai0715.mygithubclient.di.ApplicationScope
import com.kurodai0715.mygithubclient.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

const val TAG = "DefaultProfileRepository"

@Singleton
class DefaultProfileRepository @Inject constructor(
    private val networkDataSource: NetworkDataSource,
    private val localDataSource: ProfileDao,
    private val githubPreferences: GithubPreferences,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    @ApplicationScope private val scope: CoroutineScope,
) : ProfileRepository {


    override fun getPatStream(): Flow<String> {
        return githubPreferences.patFlow
    }

    override fun updatePat(pat: String) {
        scope.launch {
            withContext(dispatcher) {
                githubPreferences.updatePat(pat)
            }
        }
    }

    override fun getRetainPat(): Flow<Boolean> {
        return githubPreferences.retainPatChecked
    }

    override fun updateRetainPat(checked: Boolean) {
        scope.launch {
            withContext(dispatcher){
                githubPreferences.updateRetainPat(checked)
            }
        }
    }

    /**
     * サーバーからデータをロードして、ローカルに保存する。
     *
     * ローカルのデータは保存する前にクリアする。
     */
    override suspend fun getProfile() {
        Log.v(TAG, "refresh is started.")

        withContext(dispatcher) {
            githubPreferences.patFlow.collect { pat ->
                val patHeader = "Bearer $pat"
                val remoteProfile = networkDataSource.loadProfile(patHeader)
                Log.d(TAG, "remoteProfile = $remoteProfile")

                // DB へ登録する。
                localDataSource.upsert(remoteProfile.toLocal())
            }
        }
    }

    override fun getProfileStream(): Flow<Profile?> {
        return localDataSource.observe().map {
            Log.v(TAG, "localDataSource is changed.")
            it?.toExternal()
        }
    }
}