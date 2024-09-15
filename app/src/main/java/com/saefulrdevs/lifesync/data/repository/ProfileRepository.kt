package com.saefulrdevs.lifesync.data.repository

import android.util.Log
import com.saefulrdevs.lifesync.data.dao.ProfileDao
import com.saefulrdevs.lifesync.data.model.Profile
import javax.inject.Inject

class ProfileRepository @Inject constructor(private val profileDao: ProfileDao) {
    suspend fun insertProfile(profile: Profile): Boolean {
        return try {
            val rowId = profileDao.insertProfile(profile)
            Log.d("ProfileRepository", "Row ID: $rowId")
            rowId.toLong() != -1L
        } catch (e: Exception) {
            Log.e("ProfileRepository", "Error inserting profile: ${e.message}", e)
            false
        }
    }

    suspend fun updateProfile(profile: Profile): Boolean {
        return try {
            val rowsUpdated = profileDao.updateProfile(profile)
            Log.d("ProfileRepository", "Rows updated: $rowsUpdated")
            rowsUpdated > 0
        } catch (e: Exception) {
            Log.e("ProfileRepository", "Error updating profile: ${e.message}", e)
            false
        }
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