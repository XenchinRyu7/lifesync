package com.saefulrdevs.lifesync.ui.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.saefulrdevs.lifesync.data.database.DatabaseClient
import com.saefulrdevs.lifesync.data.repository.TaskGroupRepository
import com.saefulrdevs.lifesync.data.repository.TaskRepository
import com.saefulrdevs.lifesync.databinding.FragmentHomeBinding
import com.saefulrdevs.lifesync.viewmodel.home.CardInProgressAdapter
import com.saefulrdevs.lifesync.viewmodel.home.CardTaskGroupAdapter
import com.saefulrdevs.lifesync.viewmodel.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var cardTaskGroupAdapter: CardTaskGroupAdapter
    private lateinit var cardInProgressAdapter: CardInProgressAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        cardTaskGroupAdapter = CardTaskGroupAdapter()
        cardInProgressAdapter = CardInProgressAdapter()

        binding.recyclerViewTaskGroup.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = cardTaskGroupAdapter
        }

        binding.recyclerViewInProgress.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = cardInProgressAdapter
        }

        viewModel.cardListTaskGroup.observe(viewLifecycleOwner) { cards ->
            if (cards.isNullOrEmpty()) {
                binding.tvEmptyMessage.visibility = View.VISIBLE
                binding.recyclerViewInProgress.visibility = View.GONE
            } else {
                binding.tvEmptyMessage.visibility = View.GONE
                binding.recyclerViewTaskGroup.visibility = View.VISIBLE
                cardTaskGroupAdapter.setCards(cards)
            }
        }

        viewModel.cardInProgress.observe(viewLifecycleOwner) { cards ->
            if (cards.isNullOrEmpty()) {
                binding.tvEmptyMessageTaskGroup.visibility = View.VISIBLE
                binding.recyclerViewTaskGroup.visibility = View.GONE
            } else {
                binding.tvEmptyMessageTaskGroup.visibility = View.GONE
                binding.recyclerViewInProgress.visibility = View.VISIBLE
                cardInProgressAdapter.setCards(cards)
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

