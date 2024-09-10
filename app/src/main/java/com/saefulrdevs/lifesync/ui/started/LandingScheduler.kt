package com.saefulrdevs.lifesync.ui.started

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.saefulrdevs.lifesync.databinding.FragmentLandingSchedulerBinding
import com.saefulrdevs.lifesync.ui.auth.AuthActivity


class LandingScheduler : Fragment() {

    private var _binding: FragmentLandingSchedulerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLandingSchedulerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnNext.setOnClickListener {
            val intent = Intent(requireContext(), AuthActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

}