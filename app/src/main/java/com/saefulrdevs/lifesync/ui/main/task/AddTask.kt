package com.saefulrdevs.lifesync.ui.main.task

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
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
import com.saefulrdevs.lifesync.viewmodel.home.HomeViewModelFactory
import com.saefulrdevs.lifesync.viewmodel.task.AddTaskAdapter
import com.saefulrdevs.lifesync.viewmodel.task.TaskViewModel
import com.saefulrdevs.lifesync.viewmodel.task.TaskViewModelFactory

class AddTask : Fragment() {

    private var _binding: FragmentAddTaskBinding? = null
    private val binding get() = _binding!!

    private lateinit var taskViewModel: TaskViewModel
    private lateinit var taskAdapter: AddTaskAdapter

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddTaskBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Inisialisasi DAO dengan DatabaseClient
        val taskDao = DatabaseClient.getInstance(requireContext()).taskDao()
        val taskGroupDao = DatabaseClient.getInstance(requireContext()).taskGroupDao()

        // Inisialisasi TaskRepository dengan DAO
        val taskRepository = TaskRepository(taskDao)
        val taskGroupRepository = TaskGroupRepository(taskGroupDao)

        // Inisialisasi ViewModelFactory menggunakan TaskRepository
        val factory =
            TaskViewModelFactory(requireActivity().application, taskRepository, taskGroupRepository)
        taskViewModel = ViewModelProvider(this, factory)[TaskViewModel::class.java]

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

            // Simpan task ke data
            // base melalui ViewModel
            taskViewModel.insertTask(task)

            // Kembali ke halaman sebelumnya
            navController.popBackStack()
        }

        taskAdapter = AddTaskAdapter()
        binding.recyclerViewTasks.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewTasks.adapter = taskAdapter

        binding.cardTaskGroup.setOnClickListener {
            if (binding.expandableLayout.visibility == View.GONE) {
                // Expand layout dan load data dari database
                binding.expandableLayout.visibility = View.VISIBLE
                binding.iconGroup.visibility = View.GONE
                binding.iconDropdown.visibility = View.GONE
                loadDataFromDatabase()
            } else {
                // Collapse layout
                binding.titleTaskGroup.visibility = View.VISIBLE
                binding.expandableLayout.visibility = View.GONE
                binding.iconGroup.visibility = View.VISIBLE
                binding.iconDropdown.visibility = View.VISIBLE
            }
        }

        return root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun loadDataFromDatabase() {
        taskViewModel.getAllTaskGroups().observe(viewLifecycleOwner, Observer { taskGroups ->
            if (taskGroups.isNullOrEmpty()) {
                binding.titleTaskGroup.text = "Belum ada project group"
                binding.titleTaskGroup.visibility =
                    View.VISIBLE
            } else {
                binding.titleTaskGroup.visibility =
                    View.GONE
                taskAdapter.submitList(taskGroups)
            }
        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
