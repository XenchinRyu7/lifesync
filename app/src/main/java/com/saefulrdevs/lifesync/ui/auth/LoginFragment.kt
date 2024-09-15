package com.saefulrdevs.lifesync.ui.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.saefulrdevs.lifesync.data.dao.ProfileDao
import com.saefulrdevs.lifesync.data.database.DatabaseClient
import com.saefulrdevs.lifesync.data.repository.ProfileRepository
import com.saefulrdevs.lifesync.databinding.FragmentLoginBinding
import com.saefulrdevs.lifesync.ui.main.MainActivity
import com.saefulrdevs.lifesync.viewmodel.profile.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val profileViewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        val btnLogin = binding.btnLogin
        btnLogin.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
            requireActivity().finish()

//            profileViewModel.getProfileByEmail(email) { profile ->
//                requireActivity().runOnUiThread {
//                    if (profile != null) {
//                        if (profile.password == password) {
//                            Toast.makeText(requireContext(), "Login berhasil", Toast.LENGTH_SHORT)
//                                .show()
//
//                            val sharedPreferences = requireContext().getSharedPreferences(
//                                "LoginPrefs",
//                                Context.MODE_PRIVATE
//                            )
//                            val editor = sharedPreferences.edit()
//                            editor.putString("userId", profile.id)
//                            editor.apply()
//
//                            val intent = Intent(requireContext(), MainActivity::class.java)
//                            startActivity(intent)
//                            requireActivity().finish()
//                        } else {
//                            Toast.makeText(requireContext(), "Password salah", Toast.LENGTH_SHORT)
//                                .show()
//                        }
//                    } else {
//                        Toast.makeText(
//                            requireContext(),
//                            "Email tidak ditemukan",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                    }
//                }
//            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
