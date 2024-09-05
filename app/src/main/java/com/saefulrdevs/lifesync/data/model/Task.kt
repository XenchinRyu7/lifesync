package com.saefulrdevs.lifesync.data.model

import java.util.UUID

//data class Task(
//    val id: String = UUID.randomUUID().toString(),
//    val title: String,
//    val groupId: Int,
//    val progress: Int,
//    val description: String? = null,
//    val dueDate: String? = null,
//    val isCompleted: Boolean = false
//)

import androidx.room.*

@Entity(
    tableName = "task",
    foreignKeys = [ForeignKey(
        entity = TaskGroup::class,
        parentColumns = ["idGroup"],
        childColumns = ["groupId"],
        onDelete = ForeignKey.SET_NULL
    )]
)
data class Task(
    @PrimaryKey(autoGenerate = false) val id: String = UUID.randomUUID().toString(),
    val title: String,
    val description: String? = null,
    val dueDate: String? = null,
    val isCompleted: Boolean = false,
    val progress: Int = 0,
    @ColumnInfo(index = true) val groupId: Int? = null // Bisa null
)

