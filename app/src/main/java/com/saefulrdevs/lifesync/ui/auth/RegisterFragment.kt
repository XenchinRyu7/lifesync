package com.saefulrdevs.lifesync.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.Scopes
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.Scope
import com.google.android.gms.tasks.Task
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputEditText
import com.saefulrdevs.lifesync.R
import com.saefulrdevs.lifesync.data.model.Profile
import com.saefulrdevs.lifesync.databinding.FragmentRegisterBinding
import com.saefulrdevs.lifesync.ui.main.MainActivity
import com.saefulrdevs.lifesync.ui.main.profile.ProfileViewModel
import com.saefulrdevs.lifesync.utils.ViewUtils
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val RC_SIGN_IN = 9001
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var profileViewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)

        profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)

        val birthDateEditText: TextInputEditText = binding.birthDateEditText
        birthDateEditText.setOnClickListener {
            ViewUtils.showDatePicker(this, birthDateEditText)
        }

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestScopes(Scope(Scopes.DRIVE_FILE))
            .requestIdToken(getString(R.string.server_client_id))
            .requestServerAuthCode(getString(R.string.server_client_id))
            .build()

        val googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)

        val btnGoogle = binding.googleBtn

        btnGoogle.setOnClickListener {
            signIn()
        }

        val btnRegister = binding.registerBtn
        btnRegister.setOnClickListener {
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }

        return binding.root
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            if (account != null) {
                val profile = Profile(
                    id = UUID.randomUUID()
                        .toString(),
                    name = account.displayName ?: "",
                    username = account.displayName ?: "",
                    password = "",
                    email = account.email ?: "",
                    avatarUrl = account.photoUrl.toString()
                )

                // Simpan profil menggunakan ViewModel
//                profileViewModel.insertProfile(profile)

                // Sign-in berhasil, Anda dapat mengakses akun dan token di sini
                // accessGoogleDrive(account)
            }
        } catch (e: ApiException) {
            // Sign-in gagal, tangani kesalahan di sini
            Log.w("SignIn", "signInResult:failed code=" + e.statusCode)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
