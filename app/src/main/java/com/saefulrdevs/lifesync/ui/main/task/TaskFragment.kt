package com.saefulrdevs.lifesync.ui.main.task

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.saefulrdevs.lifesync.databinding.FragmentTaskBinding
import com.saefulrdevs.lifesync.viewmodel.task.CardDateAdapter
import com.saefulrdevs.lifesync.viewmodel.task.TaskViewModel

class TaskFragment : Fragment() {
    private var _binding: FragmentTaskBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: TaskViewModel
    private lateinit var cardDateAdapter: CardDateAdapter

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTaskBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val navController = findNavController()

        viewModel = ViewModelProvider(this)[TaskViewModel::class.java]

        cardDateAdapter = CardDateAdapter()

        binding.recyclerViewDate.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = cardDateAdapter
        }

        viewModel.cardListDate.observe(viewLifecycleOwner) { cards ->
            cardDateAdapter.setCards(cards)
        }

        val btnBack = binding.btnBack
        btnBack.setOnClickListener {
            navController.popBackStack()
        }

        return root
    }
}