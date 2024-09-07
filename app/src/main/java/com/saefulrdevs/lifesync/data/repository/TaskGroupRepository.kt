package com.saefulrdevs.lifesync.data.repository

import androidx.lifecycle.LiveData
import com.saefulrdevs.lifesync.data.dao.TaskGroupDao
import com.saefulrdevs.lifesync.data.model.TaskGroup


class TaskGroupRepository(private val taskGroupDao: TaskGroupDao) {

    suspend fun insertTaskGroup(taskGroup: TaskGroup) {
        taskGroupDao.insertTaskGroup(taskGroup)
    }

    suspend fun updateTaskGroup(taskGroup: TaskGroup) {
        taskGroupDao.updateTaskGroup(taskGroup)
    }

    suspend fun deleteTaskGroup(taskGroup: TaskGroup) {
        taskGroupDao.deleteTaskGroup(taskGroup)
    }

    fun getTaskGroupById(groupId: Int): TaskGroup? {
        return taskGroupDao.getTaskGroupById(groupId)
    }

    fun getAllTaskGroups(): LiveData<List<TaskGroup>> {
        return taskGroupDao.getAllTaskGroups()
    }
}
