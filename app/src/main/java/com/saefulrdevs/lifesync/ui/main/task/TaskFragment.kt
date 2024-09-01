package com.saefulrdevs.lifesync.ui.main.task

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.saefulrdevs.lifesync.R
import com.saefulrdevs.lifesync.databinding.FragmentMenuBinding
import com.saefulrdevs.lifesync.databinding.FragmentTaskBinding

class TaskFragment : Fragment() {
    private var _binding: FragmentTaskBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentTaskBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val navController = findNavController()

        val btnBack = binding.btnBack

        btnBack.setOnClickListener {
            navController.popBackStack()
        }

        return root
    }

}