package com.saefulrdevs.lifesync.viewmodel.task

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saefulrdevs.lifesync.data.model.DateCard
import com.saefulrdevs.lifesync.data.model.Task
import com.saefulrdevs.lifesync.data.model.TaskGroup
import com.saefulrdevs.lifesync.data.model.TaskWithGroup
import com.saefulrdevs.lifesync.data.repository.TaskGroupRepository
import com.saefulrdevs.lifesync.data.repository.TaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
class TaskViewModel(
    application: Application,
    private val taskRepository: TaskRepository,
    private val taskGroupRepository: TaskGroupRepository
) :
    AndroidViewModel(
        application
    ) {

    private val _cardListDate = MutableLiveData<List<DateCard>>()
    val cardListDate: LiveData<List<DateCard>> get() = _cardListDate

    @RequiresApi(Build.VERSION_CODES.O)
    private fun generateDateListForCurrentMonth(): List<DateCard> {
        val dateList = mutableListOf<DateCard>()

        val today = LocalDate.now()

        val currentYear = today.year
        val currentMonth = today.monthValue

        val startDate = LocalDate.of(currentYear, currentMonth, 1)
        val endDate = startDate.withDayOfMonth(startDate.lengthOfMonth())

        var currentDate = startDate
        while (!currentDate.isAfter(endDate)) {
            val month = currentDate.month.getDisplayName(TextStyle.SHORT, Locale.getDefault())
            val day = currentDate.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault())
            dateList.add(DateCard(month, currentDate.dayOfMonth.toString(), day))
            currentDate = currentDate.plusDays(1)
        }

        return dateList
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun loadCardDate() {
        val setDate = generateDateListForCurrentMonth()

        _cardListDate.value = setDate
    }

    init {
        loadCardDate()
    }

    fun insertTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            taskRepository.insertTask(task)
        }
    }

    fun getTaskWithGroup(taskId: String): LiveData<TaskWithGroup> {
        return taskRepository.getTaskWithGroup(taskId)
    }

    fun getAllTasksWithGroup(): LiveData<List<TaskWithGroup>> {
        return taskRepository.getAllTasksWithGroup()
    }

    fun getAllTaskGroups(): LiveData<List<TaskGroup>> {
        return taskGroupRepository.getAllTaskGroups()
    }
}
