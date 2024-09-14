package com.saefulrdevs.lifesync.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.saefulrdevs.lifesync.data.model.Profile

@Dao
interface ProfileDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProfile(profile: Profile)

    @Update
    suspend fun updateProfile(profile: Profile)

    @Delete
    fun deleteProfile(profile: Profile)

    @Query("SELECT * FROM profile WHERE id = :profileId")
    fun getProfileById(profileId: String): Profile

    @Query("SELECT * FROM profile WHERE email = :email")
    fun getProfileByEmail(email: String): Profile

}