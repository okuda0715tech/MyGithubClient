package com.kurodai0715.mygithubclient.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * Profile テーブルを含む Room データベース.
 *
 * Note that exportSchema should be true in production databases.
 */

@Database(entities = [LocalProfile::class, LocalUserRepo::class], version = 1, exportSchema = false)
abstract class GithubDatabase : RoomDatabase() {

    abstract fun profileDao(): ProfileDao
}