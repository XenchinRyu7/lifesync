package com.saefulrdevs.lifesync

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {
    var userId: String? = null

    override fun onCreate() {
        super.onCreate()
        val sharedPreferences =
            this.getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE)
        userId = sharedPreferences.getString("userId", null)
    }
}
