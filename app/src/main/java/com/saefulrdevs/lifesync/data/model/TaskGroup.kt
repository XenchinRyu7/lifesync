package com.saefulrdevs.lifesync.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task_group")
data class TaskGroup(
    @PrimaryKey(autoGenerate = true) val idGroup: Int = 0,
    val title: String,
    val totalTask: Int = 0,
    val percentage: Int = 0,
    val icon: Int
)

