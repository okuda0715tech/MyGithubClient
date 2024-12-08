package com.kurodai0715.mygithubclient.data

import kotlinx.coroutines.flow.Flow

interface ProfileRepository {

    /**
     * サーバーからデータをロードして、ローカルに保存する。
     *
     * ローカルのデータは保存する前にクリアする。
     *
     * @return サーバーからのレスポンスコード
     */
    suspend fun loadProfile(): Int

    /**
     * ローカルに保存された user API の結果を取得する.
     */
    fun fetchProfileStream(): Flow<Profile?>

    /**
     * サーバーからデータをロードして、ローカルに保存する。
     *
     * ローカルのデータは保存する前にクリアする。
     *
     * @return サーバーからのレスポンスコード
     */
    suspend fun loadUserRepos(): Int

    /**
     * ローカルに保存された user/repos API の結果を取得する.
     */
    fun fetchUserReposStream(): Flow<List<UserRepo>?>

}