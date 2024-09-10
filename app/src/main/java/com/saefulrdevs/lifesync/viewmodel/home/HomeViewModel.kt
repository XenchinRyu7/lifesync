package com.saefulrdevs.lifesync.viewmodel.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.saefulrdevs.lifesync.data.model.TaskGroup
import com.saefulrdevs.lifesync.data.model.TaskWithGroup
import com.saefulrdevs.lifesync.data.repository.TaskGroupRepository
import com.saefulrdevs.lifesync.data.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    application: Application,
    private val taskRepository: TaskRepository,
    private val taskGroupRepository: TaskGroupRepository
) : AndroidViewModel(application) {

    val cardListTaskGroup: LiveData<List<TaskGroup>> get() = taskGroupRepository.getAllTaskGroups()

    val cardInProgress: LiveData<List<TaskWithGroup>> get() = taskRepository.getAllTasksWithGroup()
}

