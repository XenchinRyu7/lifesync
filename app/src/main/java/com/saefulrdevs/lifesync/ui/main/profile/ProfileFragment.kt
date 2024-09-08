package com.saefulrdevs.lifesync.ui.main.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.saefulrdevs.lifesync.data.database.DatabaseClient
import com.saefulrdevs.lifesync.data.repository.ProfileRepository
import com.saefulrdevs.lifesync.databinding.FragmentProfileBinding
import com.saefulrdevs.lifesync.viewmodel.profile.ProfileViewModel
import com.saefulrdevs.lifesync.viewmodel.profile.ProfileViewModelFactory

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var profileViewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        val profileDao = DatabaseClient.getInstance(requireContext()).profileDao()

        val profileRepository = ProfileRepository(profileDao)

        val factory = ProfileViewModelFactory(requireActivity().application, profileRepository)
        profileViewModel = ViewModelProvider(this, factory)[ProfileViewModel::class.java]



        return binding.root
    }

}