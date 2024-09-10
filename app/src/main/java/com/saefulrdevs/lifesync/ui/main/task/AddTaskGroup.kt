package com.saefulrdevs.lifesync.ui.main.task

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.saefulrdevs.lifesync.data.model.TaskGroup
import com.saefulrdevs.lifesync.databinding.FragmentAddTaskGroupBinding
import com.saefulrdevs.lifesync.utils.ViewUtils
import com.saefulrdevs.lifesync.viewmodel.task.TaskGroupViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddTaskGroup : Fragment() {

    private var _binding: FragmentAddTaskGroupBinding? = null
    private val binding get() = _binding!!

    private val taskGroupViewModel: TaskGroupViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddTaskGroupBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val navController = findNavController()

        val btnBack = binding.btnBack
        btnBack.setOnClickListener {
            navController.popBackStack()
        }

        val cardStartDate = binding.cardStartDate
        val tvStartDate = binding.startDate
        cardStartDate.setOnClickListener {
            ViewUtils.showDatePicker(this, null, cardStartDate, tvStartDate)
        }

        val cardEndDate = binding.cardEndDate
        val tvEndDate = binding.endDate
        cardEndDate.setOnClickListener {
            ViewUtils.showDatePicker(this, null, cardEndDate, tvEndDate)
        }

        binding.btnSave.setOnClickListener {
            val title = binding.textFiledProjectName.text.toString()
            val description = binding.textFieldProjectDescription.text.toString()
            val startDate = binding.startDate.text.toString()
            val endDate = binding.endDate.text.toString()

            val taskGroup = TaskGroup(
                title = title,
                description = description,
                startDate = startDate,
                endDate = endDate
            )

            taskGroupViewModel.insertTaskGroup(taskGroup)

            navController.popBackStack()
        }

        return root
    }
}