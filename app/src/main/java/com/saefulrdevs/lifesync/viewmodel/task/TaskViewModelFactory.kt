package com.saefulrdevs.lifesync.viewmodel.task

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.saefulrdevs.lifesync.data.repository.TaskGroupRepository
import com.saefulrdevs.lifesync.data.repository.TaskRepository
import com.saefulrdevs.lifesync.viewmodel.home.HomeViewModel

class TaskViewModelFactory(
    private val application: Application,
    private val taskRepository: TaskRepository,
    private val taskGroupRepository: TaskGroupRepository
) : ViewModelProvider.Factory {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskViewModel::class.java)) {
            return TaskViewModel(application, taskRepository, taskGroupRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
