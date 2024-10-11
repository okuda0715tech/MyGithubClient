package com.kurodai0715.mygithubclient.data.source.local

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.kurodai0715.mygithubclient.di.IoDispatcher
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

// TODO Hilt で依存関係の注入ができないか検討する。
val Context.githubPrefDataStore: DataStore<Preferences> by preferencesDataStore(name = "github_preferences")

// Preference のキー名
private const val KEY_NAME_PERSONAL_ACCESS_TOKEN = "pat"
private const val KEY_NAME_PAT_VISIBILITY = "pat_visibility"
private const val KEY_NAME_RETAIN_PAT = "retain_pat"

// Preference のキー名とデータ型をバインドしたオブジェクト
val PAT = stringPreferencesKey(KEY_NAME_PERSONAL_ACCESS_TOKEN)
val PAT_VISIBILITY = booleanPreferencesKey(KEY_NAME_PAT_VISIBILITY)
val RETAIN_PAT = booleanPreferencesKey(KEY_NAME_RETAIN_PAT)

private const val TAG = "GithubPreferences"

data class GithubPreferences(val pat: String, val patVisibility: Boolean, val retainPat: Boolean)

class GithubPreferencesRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
) {

    suspend fun updatePat(pat: String) {
        withContext(dispatcher) {
            context.githubPrefDataStore.edit { githubPref ->
                githubPref[PAT] = pat
            }
        }
    }

    suspend fun updatePatVisibility(checked: Boolean) {
        withContext(dispatcher) {
            context.githubPrefDataStore.edit { githubPref ->
                Log.d(TAG, "save githubPref[PAT_VISIBILITY] (${githubPref[PAT_VISIBILITY]})}")
                githubPref[PAT_VISIBILITY] = checked
            }
        }
    }

    suspend fun updateRetainPat(checked: Boolean) {
        withContext(dispatcher) {
            context.githubPrefDataStore.edit { githubPref ->
                Log.d(TAG, "save githubPref[RETAIN_PAT] (${githubPref[RETAIN_PAT]})}")
                githubPref[RETAIN_PAT] = checked
            }
        }
    }

    val githubPreferences: Flow<GithubPreferences> = context.githubPrefDataStore.data
        .map { githubPref ->
            val pat = githubPref[PAT] ?: ""
            val patVisibility = githubPref[PAT_VISIBILITY] ?: false
            val retainPat = githubPref[RETAIN_PAT] ?: false
            val githubPreferences =
                GithubPreferences(pat = pat, patVisibility = patVisibility, retainPat = retainPat)
            Log.d(TAG, "patVisibility = $patVisibility, retainPat = $retainPat")
            githubPreferences
        }
}