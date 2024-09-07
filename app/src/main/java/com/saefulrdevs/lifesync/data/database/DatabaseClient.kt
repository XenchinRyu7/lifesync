package com.saefulrdevs.lifesync.data.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.saefulrdevs.lifesync.data.dao.TaskDao
import com.saefulrdevs.lifesync.data.dao.TaskGroupDao

class DatabaseClient private constructor(context: Context) {

    private val appDatabase: AppDatabase = Room.databaseBuilder(
        context.applicationContext,
        AppDatabase::class.java, "LifeSync.db"
    ).setJournalMode(RoomDatabase.JournalMode.WRITE_AHEAD_LOGGING)
        .fallbackToDestructiveMigration()
        .build()

    companion object {
        @Volatile
        private var INSTANCE: DatabaseClient? = null

        fun getInstance(context: Context): DatabaseClient {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: DatabaseClient(context).also { INSTANCE = it }
            }
        }
    }

    fun taskDao(): TaskDao = appDatabase.taskDao()
    fun taskGroupDao(): TaskGroupDao = appDatabase.taskGroupDao()
}

