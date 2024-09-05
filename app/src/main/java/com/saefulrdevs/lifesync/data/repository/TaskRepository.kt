package com.saefulrdevs.lifesync.data.repository

import com.saefulrdevs.lifesync.data.dao.TaskDao
import com.saefulrdevs.lifesync.data.dao.TaskGroupDao
import com.saefulrdevs.lifesync.data.model.Task
import com.saefulrdevs.lifesync.data.model.TaskGroup


class TaskRepository(private val taskDao: TaskDao, private val taskGroupDao: TaskGroupDao) {

    suspend fun insertTask(task: Task) {
        taskDao.insertTask(task)
    }

    suspend fun updateTask(task: Task) {
        taskDao.updateTask(task)
    }

    suspend fun deleteTask(task: Task) {
        taskDao.deleteTask(task)
    }

    fun getTaskById(taskId: Int): Task? {
        return taskDao.getTaskById(taskId)
    }

    fun getAllTasks(): List<Task> {
        return taskDao.getAllTasks()
    }

    fun getTaskGroupById(groupId: Int): TaskGroup? {
        return taskGroupDao.getTaskGroupById(groupId)
    }
}


