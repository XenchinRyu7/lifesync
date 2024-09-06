package com.saefulrdevs.lifesync.viewmodel.home

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.saefulrdevs.lifesync.data.dao.TaskDao
import com.saefulrdevs.lifesync.data.dao.TaskGroupDao
import com.saefulrdevs.lifesync.data.repository.TaskRepository

class HomeViewModelFactory(
    private val application: Application,
    private val taskRepository: TaskRepository // Hanya taskRepository yang dikirim
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(application, taskRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}



