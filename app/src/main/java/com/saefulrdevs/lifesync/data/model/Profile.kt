package com.saefulrdevs.lifesync.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(
    tableName = "profile",
)
data class Profile(
    @PrimaryKey(autoGenerate = false) val id: String = UUID.randomUUID().toString(),
    val name: String,
    val username: String,
    val password: String? = null,
    val email: String? = null,
    val pin: Int? = null,
    val avatarUrl: String? = null
)
