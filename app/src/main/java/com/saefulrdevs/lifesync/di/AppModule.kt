package com.saefulrdevs.lifesync.di

import android.content.Context
import androidx.room.Room
import com.saefulrdevs.lifesync.data.dao.ProfileDao
import com.saefulrdevs.lifesync.data.dao.TaskDao
import com.saefulrdevs.lifesync.data.dao.TaskGroupDao
import com.saefulrdevs.lifesync.data.database.AppDatabase
import com.saefulrdevs.lifesync.data.database.DatabaseClient
import com.saefulrdevs.lifesync.data.repository.TaskRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabaseClient(@ApplicationContext context: Context): DatabaseClient {
        return DatabaseClient.getInstance(context)
    }

    @Provides
    @Singleton
    fun provideTaskDao(databaseClient: DatabaseClient): TaskDao {
        return databaseClient.taskDao()
    }

    @Provides
    @Singleton
    fun provideTaskGroupDao(databaseClient: DatabaseClient): TaskGroupDao {
        return databaseClient.taskGroupDao()
    }

    @Provides
    @Singleton
    fun provideProfileDao(databaseClient: DatabaseClient): ProfileDao {
        return databaseClient.profileDao()
    }
}

