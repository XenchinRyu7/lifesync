package com.saefulrdevs.lifesync.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.Scopes
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.Scope
import com.google.android.gms.tasks.Task
import com.saefulrdevs.lifesync.R
import com.saefulrdevs.lifesync.data.model.Profile
import com.saefulrdevs.lifesync.databinding.FragmentRegisterBinding
import com.saefulrdevs.lifesync.utils.EmailUtils
import com.saefulrdevs.lifesync.utils.SendMail
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

            if (!isValidEmail(email)) {
                Toast.makeText(
                    context,
                    "Email tidak valid, pastikan formatnya benar!",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            profileViewModel.insertProfile(profile) { isSuccess ->
                if (isSuccess) {
                    val verificationCode = EmailUtils.generateVerificationCode()

                    var isSendSuccess = false

                    EmailUtils.sendVerificationEmail(
                        requireContext(),
                        email,
                        verificationCode,
                        object : SendMail.OnMailSendListener {
                            override fun onSuccess() {
                                EmailUtils.saveVerificationCodeToSharedPreferences(
                                    requireContext(),
                                    verificationCode
                                )
                                Toast.makeText(
                                    requireContext(),
                                    "Email berhasil dikirim!",
                                    Toast.LENGTH_SHORT
                                ).show()
                                isSendSuccess = true
                                navigateToNextActivity(email, isSendSuccess)
                            }

                            override fun onFailure() {
                                Toast.makeText(
                                    requireContext(),
                                    "Gagal mengirim email. Silakan coba lagi.",
                                    Toast.LENGTH_SHORT
                                ).show()
                                navigateToNextActivity(email, isSendSuccess)
                            }
                        })

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

    private fun navigateToNextActivity(email: String, isSendSuccess: Boolean) {
        val bundle = Bundle().apply {
            putString("email", email)
            putBoolean("isSendSuccess", isSendSuccess)
        }
        val intent = Intent(requireContext(), VerificationActivity::class.java)
        intent.putExtras(bundle)
        startActivity(intent)
        requireActivity().finish()
    }

    private fun isValidEmail(email: String): Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        return email.matches(emailPattern.toRegex())
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
