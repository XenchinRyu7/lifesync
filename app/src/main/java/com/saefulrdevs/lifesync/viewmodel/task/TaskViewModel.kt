package com.saefulrdevs.lifesync.viewmodel.task

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.saefulrdevs.lifesync.data.model.DateCard
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
class TaskViewModel : ViewModel() {

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
}
