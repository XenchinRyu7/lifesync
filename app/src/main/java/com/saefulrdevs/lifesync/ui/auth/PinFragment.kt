package com.saefulrdevs.lifesync.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.saefulrdevs.lifesync.databinding.FragmentPinBinding
import com.saefulrdevs.lifesync.viewmodel.profile.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import com.saefulrdevs.lifesync.data.model.Profile
import com.saefulrdevs.lifesync.utils.CodeInputUtils

@AndroidEntryPoint
class PinFragment : Fragment() {

    private var _binding: FragmentPinBinding? = null
    private val binding get() = _binding!!
    private val profileViewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPinBinding.inflate(inflater, container, false)

        val userId = arguments?.getString("userId") ?: ""

        Log.d("User Id", "User ID: $userId")

        var isConfirmingPin = false
        var firstPin = ""

        binding.btnSubmit.setOnClickListener {

            val enteredPin =
                binding.pin1.text.toString() + binding.pin2.text.toString() + binding.pin3.text.toString() + binding.pin4.text.toString()

            if (!isConfirmingPin) {
                if (enteredPin.isNotEmpty()) {
                    firstPin = enteredPin
                    isConfirmingPin =
                        true
                    binding.textViewDescription.text = "Confirm PIN"
                    binding.btnSubmit.text = "Confirm"
                    clearPinFields()
                } else {
                    binding.textViewDescription.text = "Please enter a valid PIN"
                }
            } else {
                if (enteredPin == firstPin) {
                    binding.textViewDescription.text = "PIN Confirmed"
                    profileViewModel.getProfileById(userId) { profile ->
                        val fullName = profile?.username ?: ""
                        val birthDay = profile?.birthDay
                        val phoneNumber = profile?.phoneNumber
                        val email = profile?.email
                        val avatarUrl = profile?.avatarUrl
                        val password = profile?.password

                        val updateProfile = Profile(
                            id = userId,
                            username = fullName,
                            birthDay = birthDay,
                            phoneNumber = phoneNumber,
                            email = email,
                            avatarUrl = avatarUrl,
                            pin = enteredPin.toInt(),
                            password = password
                        )

                        profileViewModel.updateProfile(updateProfile) { isSuccess ->
                            if (isSuccess) {
                                Toast.makeText(
                                    context,
                                    "Registrasi Berhasil, silahkan login",
                                    Toast.LENGTH_SHORT
                                ).show()
                                val intent =
                                    Intent(requireContext(), AuthActivity::class.java)
                                startActivity(intent)
                                requireActivity().finish()
                            } else {
                                Toast.makeText(
                                    context,
                                    "Silahkan coba kembali untuk set pin",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                } else {
                    binding.textViewDescription.text = "PIN does not match. Please try again."
                    clearPinFields()
                    isConfirmingPin = false
                    binding.btnSubmit.text = "Set PIN"
                }
            }
        }

        CodeInputUtils.setupPinInputListeners(
            listOf(binding.pin1, binding.pin2, binding.pin3, binding.pin4)
        )

        return binding.root
    }

    private fun clearPinFields() {
        binding.pin1.text?.clear()
        binding.pin2.text?.clear()
        binding.pin3.text?.clear()
        binding.pin4.text?.clear()
    }
}