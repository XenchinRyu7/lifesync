package com.saefulrdevs.lifesync.viewmodel.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.saefulrdevs.lifesync.R
import com.saefulrdevs.lifesync.data.dao.TaskDao
import com.saefulrdevs.lifesync.data.dao.TaskGroupDao
import com.saefulrdevs.lifesync.data.database.DatabaseClient
import com.saefulrdevs.lifesync.data.model.Task
import com.saefulrdevs.lifesync.data.model.TaskGroup
import com.saefulrdevs.lifesync.data.model.TaskWithGroup
import com.saefulrdevs.lifesync.data.repository.TaskRepository
import java.util.UUID

class HomeViewModel(
    application: Application,
    private val taskRepository: TaskRepository // Dependensi disediakan melalui Factory
) : AndroidViewModel(application) {

    private val _cardListTaskGroup = MutableLiveData<List<TaskGroup>>()
    val cardListTaskGroup: LiveData<List<TaskGroup>> get() = _cardListTaskGroup

    private val _cardInProgress = MutableLiveData<List<TaskWithGroup>>()
    val cardInProgress: LiveData<List<TaskWithGroup>> get() = _cardInProgress

    init {
        loadCard() // Masih menggunakan dummy data untuk sementara
    }

    private fun loadCard() {
        // Dummy data sementara
        val dummyTaskGroups = listOf(
            TaskGroup(1, "Office Project", 5, 85, R.drawable.ic_task_office),
            TaskGroup(2, "Personal Project", 3, 85, R.drawable.lock_icon),
            TaskGroup(3, "Daily Study", 10, 70, R.drawable.ic_books_study)
        )

        val dummyTasks = listOf(
            TaskWithGroup(
                Task(
                    id = UUID.randomUUID().toString(),
                    title = "Design UI for app",
                    groupId = 1,
                    progress = 70
                ),
                dummyTaskGroups[0]
            ),
            TaskWithGroup(
                Task(
                    id = UUID.randomUUID().toString(),
                    title = "Finish project setup",
                    groupId = 2,
                    progress = 50
                ),
                dummyTaskGroups[1]
            ),
            TaskWithGroup(
                Task(
                    id = UUID.randomUUID().toString(),
                    title = "Learn Kotlin basics",
                    groupId = 3,
                    progress = 90
                ),
                dummyTaskGroups[2]
            )
        )

        _cardListTaskGroup.value = dummyTaskGroups
        _cardInProgress.value = dummyTasks
    }
}

