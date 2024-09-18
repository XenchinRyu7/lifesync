package com.saefulrdevs.lifesync.ui.started

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.saefulrdevs.lifesync.R
import com.saefulrdevs.lifesync.databinding.FragmentLandingMoneyManagementBinding

class MoneyManagement : Fragment() {

    private var _binding: FragmentLandingMoneyManagementBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLandingMoneyManagementBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            binding.btnNext.setOnClickListener {
                Handler(Looper.getMainLooper()).postDelayed({
                    if (findNavController().currentDestination?.id == R.id.landingScreenFragment2) {
                        findNavController().navigate(R.id.startLandingScreen3)
                    }
                }, 100)
            }
        }

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

}