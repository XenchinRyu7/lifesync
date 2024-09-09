package com.saefulrdevs.lifesync.viewmodel.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.saefulrdevs.lifesync.data.model.Profile
import com.saefulrdevs.lifesync.data.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository
) : ViewModel() {
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

    fun getProfileById(profileId: String, callback: (Profile?) -> Unit) {
        viewModelScope.launch {
            val profile = withContext(Dispatchers.IO) {
                profileRepository.getProfileById(profileId)
            }
            callback(profile)
        }
    }

    fun getProfileByEmail(email: String, callback: (Profile?) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val profile = profileRepository.getProfileByEmail(email)

            if (profile != null) {
                withContext(Dispatchers.IO) {
                    callback(profile)
                }
            } else {
                withContext(Dispatchers.Main) {
                    callback(null)
                }
            }
        }
    }
}