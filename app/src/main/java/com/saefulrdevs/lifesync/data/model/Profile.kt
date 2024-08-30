package com.saefulrdevs.lifesync.data.model

import java.util.UUID

data class Profile(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val username: String,
    val password: String? = null,
    val email: String? = null,
    val pin: Int? = null,
    val avatarUrl: String? = null
)
