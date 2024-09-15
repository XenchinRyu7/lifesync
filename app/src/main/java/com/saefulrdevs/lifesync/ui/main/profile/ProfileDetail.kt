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
import com.saefulrdevs.lifesync.utils.ViewUtils
import com.saefulrdevs.lifesync.viewmodel.profile.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.UUID

@AndroidEntryPoint
class ProfileDetail : Fragment() {

    private var _binding: FragmentProfileDetailBinding? = null
    private val binding get() = _binding!!

    private val profileViewModel: ProfileViewModel by viewModels()
    private var fullName: String? = null
    private var birthDay: String? = null
    private var phoneNumber: String? = null
    private var email: String? = null
    private var avatarUrl: String? = null
    private var pin: Int? = null
    private var password: String? = null

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
                fullName = profile?.username
                birthDay = profile?.birthDay
                phoneNumber = profile?.phoneNumber
                email = profile?.email
                avatarUrl = profile?.avatarUrl
                pin = profile?.pin
                password = profile?.password

                binding.fullName.text = fullName
                binding.tvEmail.text = email
                binding.fullNameEditText.setText(fullName)
                binding.birthDateEditText.setText(birthDay)
                binding.phoneNumberEditText.setText(birthDay)
            }
        }

        binding.birthDateEditText.setOnClickListener {
            ViewUtils.showDatePicker(this, binding.birthDateEditText)
        }

        var isEditMode = false

        binding.btnCheckUpdate.setOnClickListener {
            if (isEditMode) {
                binding.fullNameEditText.isEnabled = false
                binding.birthDateEditText.isEnabled = false
                binding.phoneNumberEditText.isEnabled = false
                binding.birthDateEditText.isClickable = false
                binding.birthDateEditText.isFocusable = false
                binding.btnUpdateProfile.isEnabled = false
                Toast.makeText(
                    context,
                    "Edit Mode Disable",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                binding.fullNameEditText.isEnabled = true
                binding.birthDateEditText.isEnabled = true
                binding.phoneNumberEditText.isEnabled = true
                binding.birthDateEditText.isClickable = true
                binding.birthDateEditText.isFocusable = true
                binding.btnUpdateProfile.isEnabled = true
                Toast.makeText(
                    context,
                    "Edit Mode Enable",
                    Toast.LENGTH_SHORT
                ).show()
            }

            isEditMode = !isEditMode
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
                        id = userId.toString(),
                        username = fullName,
                        birthDay = birthDay,
                        phoneNumber = phoneNumber,
                        email = email,
                        avatarUrl = avatarUrl,
                        pin = pin,
                        password = password
                    )

                    profileViewModel.updateProfile(updateProfile) { isSuccess ->
                        if (isSuccess) {
                            Toast.makeText(
                                context,
                                "Profile updated successfully",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(context, "Failed to update profile", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                    dialog.dismiss()
                }
                .show()
        }

        return binding.root
    }

}