package com.saefulrdevs.lifesync.data.repository

import com.saefulrdevs.lifesync.data.dao.ProfileDao
import com.saefulrdevs.lifesync.data.model.Profile

class ProfileRepository(private val profileDao: ProfileDao) {
    suspend fun insertProfile(profile: Profile) {
        profileDao.insertProfile(profile)
    }

    suspend fun updateProfile(profile: Profile) {
        profileDao.updateProfile(profile)
    }

    suspend fun deleteProfile(profile: Profile) {
        profileDao.deleteProfile(profile)
    }

    fun getProfileById(profileId: String): Profile {
        return profileDao.getProfileById(profileId)
    }

    fun getProfileByEmail(email: String): Profile {
        return profileDao.getProfileByEmail(email)
    }

}