package com.saefulrdevs.lifesync.data.model

data class Profile(
    val id: Long = 0,
    val name: String,
    val username: String,
    val password: String,
    val email: String? = null,
    val avatarUrl: String? = null
)
