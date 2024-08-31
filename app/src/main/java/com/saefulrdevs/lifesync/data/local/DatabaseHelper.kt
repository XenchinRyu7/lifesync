package com.saefulrdevs.lifesync.data.local

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        // Create the Profile table
        val createProfileTable = """
            CREATE TABLE $TABLE_PROFILE (
                $COLUMN_PROFILE_ID STRING PRIMARY KEY,
                $COLUMN_PROFILE_NAME TEXT NOT NULL,
                $COLUMN_PROFILE_USERNAME TEXT NOT NULL,
                $COLUMN_PROFILE_PASSWORD TEXT NOT NULL,
                $COLUMN_PROFILE_EMAIL TEXT,
                $COLUMN_PROFILE_AVATAR_URL TEXT
            )
        """.trimIndent()
        db.execSQL(createProfileTable)

        // Create the Task table
        val createTaskTable = """
            CREATE TABLE $TABLE_TASK (
                $COLUMN_TASK_ID INTEGER PRIMARY KEY,
                $COLUMN_TASK_TITLE TEXT NOT NULL,
                $COLUMN_TASK_DESCRIPTION TEXT,
                $COLUMN_TASK_DUE_DATE TEXT,
                $COLUMN_TASK_IS_COMPLETED INTEGER NOT NULL
            )
        """.trimIndent()
        db.execSQL(createTaskTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Handle database upgrade as needed
        db.execSQL("DROP TABLE IF EXISTS $TABLE_PROFILE")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_TASK")
        onCreate(db)
    }

    companion object {
        private const val DATABASE_NAME = "lifesync.db"
        private const val DATABASE_VERSION = 1

        const val TABLE_PROFILE = "profile"
        const val COLUMN_PROFILE_ID = "id"
        const val COLUMN_PROFILE_NAME = "name"
        const val COLUMN_PROFILE_USERNAME = "username"
        const val COLUMN_PROFILE_PASSWORD = "password"
        const val COLUMN_PROFILE_EMAIL = "email"
        const val COLUMN_PROFILE_AVATAR_URL = "avatarUrl"
        const val COLUMN_PROFILE_PIN = "pin"

        const val TABLE_TASK = "task"
        const val COLUMN_TASK_ID = "id"
        const val COLUMN_TASK_TITLE = "title"
        const val COLUMN_TASK_DESCRIPTION = "description"
        const val COLUMN_TASK_DUE_DATE = "dueDate"
        const val COLUMN_TASK_IS_COMPLETED = "isCompleted"
        const val COLUMN_TASK_GROUP = "group"
        const val COLUMN_TASK_PROGRESS = "progress"
        const val COLUMN_TASK_ICON_GROUP = "iconGroup"
    }
}
