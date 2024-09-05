package com.saefulrdevs.lifesync.data.repository

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

    fun getAllTaskGroups(): List<TaskGroup> {
        return taskGroupDao.getAllTaskGroups()
    }
}
