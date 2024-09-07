package com.saefulrdevs.lifesync.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.saefulrdevs.lifesync.data.model.Task
import com.saefulrdevs.lifesync.data.model.TaskWithGroup

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTask(task: Task): Long

    @Update
    fun updateTask(task: Task): Int

    @Delete
    fun deleteTask(task: Task): Int

    @Query("SELECT * FROM task WHERE id = :taskId")
    fun getTaskById(taskId: String): LiveData<Task>

    @Transaction
    @Query("SELECT * FROM task WHERE id = :taskId")
    fun getTaskWithGroup(taskId: String): LiveData<TaskWithGroup>

    @Transaction
    @Query("SELECT * FROM task")
    fun getAllTasksWithGroup(): LiveData<List<TaskWithGroup>>
}
