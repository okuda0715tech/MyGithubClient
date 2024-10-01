package com.kurodai0715.mygithubclient.di

import android.content.Context
import androidx.room.Room
import com.kurodai0715.mygithubclient.data.DefaultProfileRepository
import com.kurodai0715.mygithubclient.data.ProfileRepository
import com.kurodai0715.mygithubclient.data.source.local.GithubDatabase
import com.kurodai0715.mygithubclient.data.source.local.ProfileDao
import com.kurodai0715.mygithubclient.data.source.network.NetworkDataSource
import com.kurodai0715.mygithubclient.data.source.network.ProfileNetworkDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindProfileRepository(repository: DefaultProfileRepository): ProfileRepository
}

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Singleton
    @Binds
    abstract fun bindNetworkDataSource(dataSource: ProfileNetworkDataSource): NetworkDataSource
}

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): GithubDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            GithubDatabase::class.java,
            "Tasks.db"
        ).build()
    }

    @Provides
    fun provideProfileDao(database: GithubDatabase): ProfileDao = database.profileDao()
}