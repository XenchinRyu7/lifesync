package com.saefulrdevs.lifesync.data.repository

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.saefulrdevs.lifesync.data.local.DatabaseHelper
import com.saefulrdevs.lifesync.data.model.Profile

class ProfileRepository(context: Context) {
    private val dbHelper = DatabaseHelper(context)
    private val db: SQLiteDatabase = dbHelper.writableDatabase

    fun insertProfile(profile: Profile): Long {
        val values = ContentValues().apply {
            put(DatabaseHelper.COLUMN_PROFILE_NAME, profile.name)
            put(DatabaseHelper.COLUMN_PROFILE_USERNAME, profile.username)
            put(DatabaseHelper.COLUMN_PROFILE_PASSWORD, profile.password)
            put(DatabaseHelper.COLUMN_PROFILE_EMAIL, profile.email)
            put(DatabaseHelper.COLUMN_PROFILE_AVATAR_URL, profile.avatarUrl)
        }
        return db.insert(DatabaseHelper.TABLE_PROFILE, null, values)
    }

    fun updateProfile(profile: Profile): Int {
        val values = ContentValues().apply {
            put(DatabaseHelper.COLUMN_PROFILE_NAME, profile.name)
            put(DatabaseHelper.COLUMN_PROFILE_USERNAME, profile.username)
            put(DatabaseHelper.COLUMN_PROFILE_PASSWORD, profile.password)
            put(DatabaseHelper.COLUMN_PROFILE_EMAIL, profile.email)
            put(DatabaseHelper.COLUMN_PROFILE_AVATAR_URL, profile.avatarUrl)
        }
        return db.update(
            DatabaseHelper.TABLE_PROFILE,
            values,
            "${DatabaseHelper.COLUMN_PROFILE_ID} = ?",
            arrayOf(profile.id.toString())
        )
    }

    fun deleteProfile(profileId: Long): Int {
        return db.delete(
            DatabaseHelper.TABLE_PROFILE,
            "${DatabaseHelper.COLUMN_PROFILE_ID} = ?",
            arrayOf(profileId.toString())
        )
    }

    fun getProfileById(profileId: Long): Profile? {
        val cursor = db.query(
            DatabaseHelper.TABLE_PROFILE,
            null,
            "${DatabaseHelper.COLUMN_PROFILE_ID} = ?",
            arrayOf(profileId.toString()),
            null,
            null,
            null
        )
        return if (cursor.moveToFirst()) {
            Profile(
                id = cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PROFILE_ID)),
                name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PROFILE_NAME)),
                username = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PROFILE_USERNAME)),
                password = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PROFILE_PASSWORD)),
                email = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PROFILE_EMAIL)),
                avatarUrl = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PROFILE_AVATAR_URL))
            )
        } else {
            null
        }
    }
}
