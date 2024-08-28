package com.saefulrdevs.lifesync.data.repository

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.saefulrdevs.lifesync.data.local.DatabaseHelper
import com.saefulrdevs.lifesync.data.model.Task

class TaskRepository(context: Context) {
    private val dbHelper = DatabaseHelper(context)
    private val db: SQLiteDatabase = dbHelper.writableDatabase

    fun insertTask(task: Task): Long {
        val values = ContentValues().apply {
            put(DatabaseHelper.COLUMN_TASK_TITLE, task.title)
            put(DatabaseHelper.COLUMN_TASK_DESCRIPTION, task.description)
            put(DatabaseHelper.COLUMN_TASK_DUE_DATE, task.dueDate)
            put(DatabaseHelper.COLUMN_TASK_IS_COMPLETED, if (task.isCompleted) 1 else 0)
        }
        return db.insert(DatabaseHelper.TABLE_TASK, null, values)
    }

    fun updateTask(task: Task): Int {
        val values = ContentValues().apply {
            put(DatabaseHelper.COLUMN_TASK_TITLE, task.title)
            put(DatabaseHelper.COLUMN_TASK_DESCRIPTION, task.description)
            put(DatabaseHelper.COLUMN_TASK_DUE_DATE, task.dueDate)
            put(DatabaseHelper.COLUMN_TASK_IS_COMPLETED, if (task.isCompleted) 1 else 0)
        }
        return db.update(
            DatabaseHelper.TABLE_TASK,
            values,
            "${DatabaseHelper.COLUMN_TASK_ID} = ?",
            arrayOf(task.id.toString())
        )
    }

    fun deleteTask(taskId: Long): Int {
        return db.delete(
            DatabaseHelper.TABLE_TASK,
            "${DatabaseHelper.COLUMN_TASK_ID} = ?",
            arrayOf(taskId.toString())
        )
    }

    fun getTaskById(taskId: Long): Task? {
        val cursor = db.query(
            DatabaseHelper.TABLE_TASK,
            null,
            "${DatabaseHelper.COLUMN_TASK_ID} = ?",
            arrayOf(taskId.toString()),
            null,
            null,
            null
        )
        return if (cursor.moveToFirst()) {
            Task(
                id = cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TASK_ID)),
                title = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TASK_TITLE)),
                description = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TASK_DESCRIPTION)),
                dueDate = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TASK_DUE_DATE)),
                isCompleted = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TASK_IS_COMPLETED)) == 1
            )
        } else {
            null
        }
    }

    fun getAllTasks(): List<Task> {
        val tasks = mutableListOf<Task>()
        val cursor = db.query(DatabaseHelper.TABLE_TASK, null, null, null, null, null, null)
        while (cursor.moveToNext()) {
            val task = Task(
                id = cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TASK_ID)),
                title = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TASK_TITLE)),
                description = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TASK_DESCRIPTION)),
                dueDate = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TASK_DUE_DATE)),
                isCompleted = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TASK_IS_COMPLETED)) == 1
            )
            tasks.add(task)
        }
        cursor.close()
        return tasks
    }
}
