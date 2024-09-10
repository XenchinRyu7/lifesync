package com.saefulrdevs.lifesync.ui.main.profile

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.saefulrdevs.lifesync.App
import com.saefulrdevs.lifesync.databinding.FragmentProfileDetailBinding
import com.saefulrdevs.lifesync.viewmodel.profile.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileDetail : Fragment() {

    private var _binding: FragmentProfileDetailBinding? = null
    private val binding get() = _binding!!

    private val profileViewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileDetailBinding.inflate(inflater, container, false)

        val navController = findNavController()

        binding.btnBack.setOnClickListener {
            navController.popBackStack()
        }

        val app = requireContext().applicationContext as App
        val userId = app.userId

        if (userId != null) {
            profileViewModel.getProfileById(userId) { profile ->
                binding.fullName.text = profile?.username
                binding.tvEmail.text = profile?.email
                binding.fullNameEditText.setText(profile?.username)
                binding.birthDateEditText.setText(profile?.birthDay)
                binding.phoneNumberEditText.setText(profile?.phoneNumber)
            }
        }

        return binding.root
    }

}