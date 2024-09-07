package com.saefulrdevs.lifesync.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.saefulrdevs.lifesync.data.model.TaskGroup


@Dao
interface TaskGroupDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTaskGroup(taskGroup: TaskGroup): Long

    @Update
    fun updateTaskGroup(taskGroup: TaskGroup): Int

    @Delete
    fun deleteTaskGroup(taskGroup: TaskGroup): Int

    @Query("SELECT * FROM task_group WHERE idGroup = :groupId")
    fun getTaskGroupById(groupId: Int): TaskGroup?

    @Query("SELECT * FROM task_group")
    fun getAllTaskGroups(): LiveData<List<TaskGroup>>
}
