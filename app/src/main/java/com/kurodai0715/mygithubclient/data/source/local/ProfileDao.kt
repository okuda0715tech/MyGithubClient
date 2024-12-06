package com.kurodai0715.mygithubclient.data.source.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

// TODO クラス名を UserDao に変更したい。 ( API のエンドポイントが user から始まるものをこのクラスに集める予定)
@Dao
interface ProfileDao {

    @Upsert
    suspend fun upsert(profile: LocalProfile)

    @Query("SELECT * FROM profile")
    fun observe(): Flow<LocalProfile?>

    @Upsert
    suspend fun upsertUserRepos(userRepos: List<LocalUserRepo>)
}