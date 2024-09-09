package com.saefulrdevs.lifesync

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        // Inisialisasi global lain jika diperlukan
    }
}
