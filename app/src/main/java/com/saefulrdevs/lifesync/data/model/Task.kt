package com.saefulrdevs.lifesync.data.model

import java.util.UUID

data class Task(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val group: String,
    val progress: Int,
    val iconGroup: Int,
    val description: String? = null,
    val dueDate: String? = null ,
    val isCompleted: Boolean = false
)
