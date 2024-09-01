package com.saefulrdevs.lifesync.ui.main.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.replace
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.saefulrdevs.lifesync.R
import com.saefulrdevs.lifesync.databinding.FragmentMenuBinding
import com.saefulrdevs.lifesync.ui.main.task.TaskFragment

class MenuFragment : Fragment() {

    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val navController = findNavController()

        val cardViewTaskManagement: CardView = binding.cardViewTaskManagement
        val cardViewMoneyManagement: CardView = binding.cardViewMoneyManagement
        val cardViewSchedule: CardView = binding.cardViewSchedule

        cardViewTaskManagement.setOnClickListener {
            navController.navigate(R.id.action_menuFragment_to_taskFragment)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}