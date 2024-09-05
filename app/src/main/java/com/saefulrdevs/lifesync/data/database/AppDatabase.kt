package com.saefulrdevs.lifesync.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.saefulrdevs.lifesync.data.dao.TaskDao
import com.saefulrdevs.lifesync.data.dao.TaskGroupDao
import com.saefulrdevs.lifesync.data.model.Task
import com.saefulrdevs.lifesync.data.model.TaskGroup

@Database(entities = [Task::class, TaskGroup::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
    abstract fun taskGroupDao(): TaskGroupDao
}
