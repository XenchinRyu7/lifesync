package com.saefulrdevs.lifesync.viewmodel.profile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.saefulrdevs.lifesync.data.model.Profile
import com.saefulrdevs.lifesync.data.repository.ProfileRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileViewModel(
    application: Application,
    private val profileRepository: ProfileRepository
) : AndroidViewModel(application) {
    fun insertProfile(profile: Profile) {
        viewModelScope.launch(Dispatchers.IO) {
            profileRepository.insertProfile(profile)
        }
    }

    fun updateProfile(profile: Profile) {
        viewModelScope.launch(Dispatchers.IO) {
            profileRepository.updateProfile(profile)
        }
    }

    fun getProfileById(profileId: String): Profile {
        return profileRepository.getProfileById(profileId)
    }

    fun getProfileByEmail(email: String, callback: (Profile?) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val profile = profileRepository.getProfileByEmail(email)

            if (profile != null) {
                withContext(Dispatchers.IO) {
                    callback(profile) // Jika profil ditemukan
                }
            } else {
                withContext(Dispatchers.Main) {
                    callback(null)
                }
            }
        }
    }
}