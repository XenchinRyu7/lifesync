package com.saefulrdevs.lifesync.data.model

data class Task(
    val id: Long = 0,
    val title: String,
    val description: String,
    val dueDate: String,
    val isCompleted: Boolean = false
)
