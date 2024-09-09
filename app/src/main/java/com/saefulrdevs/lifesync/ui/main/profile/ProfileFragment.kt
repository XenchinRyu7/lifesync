package com.saefulrdevs.lifesync.ui.main.profile

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.saefulrdevs.lifesync.R
import com.saefulrdevs.lifesync.data.database.DatabaseClient
import com.saefulrdevs.lifesync.data.repository.ProfileRepository
import com.saefulrdevs.lifesync.databinding.FragmentProfileBinding
import com.saefulrdevs.lifesync.viewmodel.profile.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val profileViewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        val navController = findNavController()

        binding.editIcon.setOnClickListener {
            navController.navigate(R.id.profile_detail)
        }

        val sharedPreferences =
            requireContext().getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE)
        val userId = sharedPreferences.getString("userId", null)

        if (userId != null) {
            profileViewModel.getProfileById(userId) { profile ->
                binding.accountName.text = profile?.username
                binding.emailAccount.text = profile?.email
            }
        }

        return binding.root
    }
}