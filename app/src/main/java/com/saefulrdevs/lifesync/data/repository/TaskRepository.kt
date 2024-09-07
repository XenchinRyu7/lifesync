package com.saefulrdevs.lifesync.data.repository

import androidx.lifecycle.LiveData
import com.saefulrdevs.lifesync.data.dao.TaskDao
import com.saefulrdevs.lifesync.data.dao.TaskGroupDao
import com.saefulrdevs.lifesync.data.model.Task
import com.saefulrdevs.lifesync.data.model.TaskGroup
import com.saefulrdevs.lifesync.data.model.TaskWithGroup


class TaskRepository(private val taskDao: TaskDao) {

    suspend fun insertTask(task: Task) {
        taskDao.insertTask(task)
    }

    suspend fun updateTask(task: Task) {
        taskDao.updateTask(task)
    }

    suspend fun deleteTask(task: Task) {
        taskDao.deleteTask(task)
    }

    fun getTaskById(taskId: String): LiveData<Task> {
        return taskDao.getTaskById(taskId)
    }

    fun getTaskWithGroup(taskId: String): LiveData<TaskWithGroup> {
        return taskDao.getTaskWithGroup(taskId)
    }

    fun getAllTasksWithGroup(): LiveData<List<TaskWithGroup>> {
        return taskDao.getAllTasksWithGroup()
    }
}



