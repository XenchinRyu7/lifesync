package com.saefulrdevs.lifesync.ui.main.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.saefulrdevs.lifesync.R
import com.saefulrdevs.lifesync.data.model.Task
import com.saefulrdevs.lifesync.data.model.TaskGroup

class HomeViewModel : ViewModel() {

    private val _cardListTaskGroup = MutableLiveData<List<TaskGroup>>()
    val cardListTaskGroup: LiveData<List<TaskGroup>> get() = _cardListTaskGroup

    private val _cardInProgress = MutableLiveData<List<Task>>()
    val cardInProgress: LiveData<List<Task>> get() = _cardInProgress

    init {
        loadCardsTaskGroup()
        loadCardInProgress()
    }

    private fun loadCardsTaskGroup() {
        val dummyData = listOf(
            TaskGroup("Office Project", 5, 85, R.drawable.ic_task_office),
            TaskGroup("Personal project", 3, 85, R.drawable.personal_project),
            TaskGroup("Daily Study", 10, 85, R.drawable.books_study)
        )
        _cardListTaskGroup.value = dummyData
    }

    private fun loadCardInProgress() {
        val dummyData = listOf(
            Task(
                "",
                "Grocery shopping app design",
                "Office Project",
                85,
                R.drawable.ic_task_office
            ),
            Task(
                "",
                "Create React App",
                "Personal Project",
                85,
                R.drawable.personal_project
            ),
            Task("", "Study how to use Git", "Daily Study", 85, R.drawable.books_study)
        )
        _cardInProgress.value = dummyData
    }
}
