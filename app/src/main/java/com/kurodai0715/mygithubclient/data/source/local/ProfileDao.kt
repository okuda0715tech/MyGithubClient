package com.kurodai0715.mygithubclient.data.source.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface ProfileDao {

    @Upsert
    suspend fun upsert(profile: LocalProfile)

    @Query("SELECT * FROM profile")
    fun observe(): Flow<LocalProfile?>
}