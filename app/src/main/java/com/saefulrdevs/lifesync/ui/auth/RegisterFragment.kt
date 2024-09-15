package com.saefulrdevs.lifesync.ui.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.Scopes
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.Scope
import com.google.android.gms.tasks.Task
import com.google.android.material.textfield.TextInputEditText
import com.saefulrdevs.lifesync.R
import com.saefulrdevs.lifesync.data.model.Profile
import com.saefulrdevs.lifesync.databinding.FragmentRegisterBinding
import com.saefulrdevs.lifesync.ui.main.MainActivity
import com.saefulrdevs.lifesync.utils.EmailUtils
import com.saefulrdevs.lifesync.utils.ViewUtils
import com.saefulrdevs.lifesync.viewmodel.profile.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.UUID

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val RC_SIGN_IN = 9001
    private lateinit var googleSignInClient: GoogleSignInClient
    private val profileViewModel: ProfileViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)

        binding.birthDateEditText.setOnClickListener {
            ViewUtils.showDatePicker(this, binding.birthDateEditText)
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

            val fullName = binding.fullNameEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val birthDay = binding.birthDateEditText.text.toString()
            val phoneNumber = binding.phoneNumberEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            val profile = Profile(
                id = UUID.randomUUID().toString(),
                username = fullName,
                password = password,
                email = email,
                birthDay = birthDay,
                phoneNumber = phoneNumber
            )

            profileViewModel.insertProfile(profile) { isSuccess ->
                if (isSuccess) {
                    // Generate kode verifikasi
                    val verificationCode = EmailUtils.generateVerificationCode()

                    // Simpan kode di SharedPreferences
                    EmailUtils.saveVerificationCodeToSharedPreferences(
                        requireContext(),
                        verificationCode
                    )

                    // Kirim email dengan kode verifikasi
                    EmailUtils.sendVerificationEmail(email, verificationCode)

                    // Navigasi ke VerificationFragment dengan bundle
                    val bundle = Bundle().apply {
                        putString("email", email)
                    }

                    val intent = Intent(requireContext(), VerificationActivity::class.java)
                    intent.putExtras(bundle)  // Sertakan bundle dalam intent
                    startActivity(intent)
                    requireActivity().finish()
                } else {
                    Toast.makeText(
                        context,
                        "Register Gagal, silahkan coba lagi",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
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
                    username = account.displayName ?: "",
                    password = "",
                    email = account.email ?: "",
                    avatarUrl = account.photoUrl.toString()
                )

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
