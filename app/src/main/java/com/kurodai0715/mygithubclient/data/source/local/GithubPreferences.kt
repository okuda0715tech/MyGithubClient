package com.kurodai0715.mygithubclient.data.source.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

// TODO Hilt で依存関係の注入ができないか検討する。
val Context.githubPrefDataStore: DataStore<Preferences> by preferencesDataStore(name = "github_preferences")

private const val KEY_NAME_PERSONAL_ACCESS_TOKEN = "pat"

class GithubPreferences @Inject constructor(@ApplicationContext private val context: Context) {

    // example_counter とうい名前のキーを定義します。
    // Int 型の値を格納することができます。
    val PAT = stringPreferencesKey(KEY_NAME_PERSONAL_ACCESS_TOKEN)

    val patFlow: Flow<String> = context.githubPrefDataStore.data
        .map { githubPref ->
            // タイプセーフではありません。
            githubPref[PAT] ?: ""
        }

    suspend fun updatePat(pat: String) {
        context.githubPrefDataStore.edit { githubPref ->
            githubPref[PAT] = pat
        }
    }
}