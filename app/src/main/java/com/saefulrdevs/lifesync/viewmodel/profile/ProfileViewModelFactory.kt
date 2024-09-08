package com.saefulrdevs.lifesync.viewmodel.profile

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.saefulrdevs.lifesync.data.repository.ProfileRepository

class ProfileViewModelFactory(
    private val application: Application,
    private val profileRepository: ProfileRepository
) : ViewModelProvider.Factory {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            return ProfileViewModel(application, profileRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}