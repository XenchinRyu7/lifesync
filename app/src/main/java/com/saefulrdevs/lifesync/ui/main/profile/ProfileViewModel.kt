package com.saefulrdevs.lifesync.ui.main.profile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.saefulrdevs.lifesync.data.model.Profile
import com.saefulrdevs.lifesync.data.repository.ProfileRepository
import kotlinx.coroutines.launch

class ProfileViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ProfileRepository = ProfileRepository(application)

    fun insertProfile(profile: Profile) {
        viewModelScope.launch {
            repository.insertProfile(profile)
        }
    }

    // Tambahkan fungsi lain seperti updateProfile, deleteProfile, dll.
}