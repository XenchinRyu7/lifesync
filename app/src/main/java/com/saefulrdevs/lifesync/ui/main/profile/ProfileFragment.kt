package com.saefulrdevs.lifesync.ui.main.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.saefulrdevs.lifesync.App
import com.saefulrdevs.lifesync.R
import com.saefulrdevs.lifesync.databinding.FragmentProfileBinding
import com.saefulrdevs.lifesync.ui.auth.AuthActivity
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

        val app = requireContext().applicationContext as App
        val userId = app.userId

        if (userId != null) {
            profileViewModel.getProfileById(userId) { profile ->
                binding.accountName.text = profile?.username
                binding.emailAccount.text = profile?.email
            }
        }
        binding.cardLogOut.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Logout")
                .setMessage("Apakah anda yakin ingin logout?")
                .setIcon(R.drawable.ic_logout)
                .setNegativeButton("Cancel") { dialog, _ ->
                    dialog.dismiss()
                }
                .setPositiveButton("Ok") { dialog, _ ->
                    app.userId = null
                    val intent = Intent(requireContext(), AuthActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                }
                .show()
        }

        return binding.root
    }
}