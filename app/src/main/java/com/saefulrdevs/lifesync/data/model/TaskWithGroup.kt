package com.saefulrdevs.lifesync.data.model

import androidx.room.Embedded
import androidx.room.Relation

data class TaskWithGroup(
    @Embedded val task: Task,
    @Relation(
        parentColumn = "groupId",
        entityColumn = "idGroup"
    )
    val taskGroup: TaskGroup?
)

