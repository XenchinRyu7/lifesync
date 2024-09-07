package com.saefulrdevs.lifesync.viewmodel.task

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saefulrdevs.lifesync.data.model.TaskGroup
import com.saefulrdevs.lifesync.data.repository.TaskGroupRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskGroupViewModel(
    application: Application,
    private val taskGroupRepository: TaskGroupRepository
) : AndroidViewModel(application) {
    fun getAllTaskGroups(): LiveData<List<TaskGroup>> {
        return taskGroupRepository.getAllTaskGroups()
    }

    fun insertTaskGroup(taskGroup: TaskGroup) {
        viewModelScope.launch(Dispatchers.IO) {
            taskGroupRepository.insertTaskGroup(taskGroup)
        }
    }
}