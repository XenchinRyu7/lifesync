package com.saefulrdevs.lifesync.ui.main.task

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.saefulrdevs.lifesync.R
import com.saefulrdevs.lifesync.data.database.DatabaseClient
import com.saefulrdevs.lifesync.data.model.Task
import com.saefulrdevs.lifesync.data.repository.TaskGroupRepository
import com.saefulrdevs.lifesync.data.repository.TaskRepository
import com.saefulrdevs.lifesync.databinding.FragmentAddTaskBinding
import com.saefulrdevs.lifesync.utils.ViewUtils
import com.saefulrdevs.lifesync.viewmodel.home.HomeViewModel
import com.saefulrdevs.lifesync.viewmodel.task.AddTaskAdapter
import com.saefulrdevs.lifesync.viewmodel.task.TaskViewModel
import com.saefulrdevs.lifesync.viewmodel.task.TaskViewModelFactory
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddTask : Fragment() {

    private var _binding: FragmentAddTaskBinding? = null
    private val binding get() = _binding!!

    private val taskViewModel: TaskViewModel by viewModels()
    private lateinit var groupAdapter: ArrayAdapter<String>

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddTaskBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val navController = findNavController()

        binding.btnBack.setOnClickListener {
            navController.popBackStack()
        }

        binding.cardStartDate.setOnClickListener {
            ViewUtils.showDatePicker(this, null, binding.cardStartDate, binding.startDate)
        }

        binding.cardEndDate.setOnClickListener {
            ViewUtils.showDatePicker(this, null, binding.cardEndDate, binding.endDate)
        }

        binding.btnSave.setOnClickListener {
            val title = binding.textFiledProjectName.text.toString()
            val description = binding.textFieldProjectDescription.text.toString()
            val startDate = binding.startDate.text.toString()
            val endDate = binding.endDate.text.toString()

            // Membuat objek task baru
            val task = Task(
                title = title,
                description = description,
                startDate = startDate,
                endDate = endDate
            )

            taskViewModel.insertTask(task)

            navController.popBackStack()
        }
        loadDataFromDatabase()

        return root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun loadDataFromDatabase() {
        taskViewModel.getAllTaskGroups().observe(viewLifecycleOwner, Observer { taskGroups ->
            if (!taskGroups.isNullOrEmpty()) {
                val groupNames = taskGroups.map { it.title }

                groupAdapter = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_dropdown_item_1line,
                    groupNames
                )

                binding.listGroup.setAdapter(groupAdapter)

                binding.listGroup.setOnClickListener {
                    binding.listGroup.showDropDown()
                }
            }
        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
