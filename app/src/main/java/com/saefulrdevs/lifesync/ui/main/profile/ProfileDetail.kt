package com.saefulrdevs.lifesync.ui.main.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.saefulrdevs.lifesync.App
import com.saefulrdevs.lifesync.R
import com.saefulrdevs.lifesync.data.model.Profile
import com.saefulrdevs.lifesync.databinding.FragmentProfileDetailBinding
import com.saefulrdevs.lifesync.ui.auth.AuthActivity
import com.saefulrdevs.lifesync.viewmodel.profile.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.UUID

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

        binding.btnUpdateProfile.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Update")
                .setMessage("Apakah anda yakin mengubah data?")
                .setIcon(R.drawable.ic_logout)
                .setNegativeButton("Cancel") { dialog, _ ->
                    dialog.dismiss()
                }
                .setPositiveButton("Ok") { dialog, _ ->

                    val fullName = binding.fullNameEditText.text.toString()
                    val birthDay = binding.birthDateEditText.text.toString()
                    val phoneNumber = binding.phoneNumberEditText.text.toString()

                    val updateProfile = Profile(
                        username = fullName,
                        birthDay = birthDay,
                        phoneNumber = phoneNumber
                    )

                    profileViewModel.updateProfile(updateProfile) { isSuccess ->
                        if (isSuccess) {
                            // Berhasil memperbarui profile
                            Toast.makeText(context, "Profile updated successfully", Toast.LENGTH_SHORT).show()
                        } else {
                            // Gagal memperbarui profile
                            Toast.makeText(context, "Failed to update profile", Toast.LENGTH_SHORT).show()
                        }
                    }
                    dialog.dismiss()
                }
                .show()
        }

        return binding.root
    }

}