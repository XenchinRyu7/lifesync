package com.saefulrdevs.lifesync.ui.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.saefulrdevs.lifesync.databinding.FragmentHomeBinding
import com.saefulrdevs.lifesync.viewmodel.home.CardInProgressAdapter
import com.saefulrdevs.lifesync.viewmodel.home.CardTaskGroupAdapter
import com.saefulrdevs.lifesync.viewmodel.home.HomeViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: HomeViewModel
    private lateinit var cardTaskGroupAdapter: CardTaskGroupAdapter
    private lateinit var cardInProgressAdapter: CardInProgressAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

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
            cardTaskGroupAdapter.setCards(cards)
        }

        viewModel.cardInProgress.observe(viewLifecycleOwner) { cards ->
            cardInProgressAdapter.setCards(cards)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
