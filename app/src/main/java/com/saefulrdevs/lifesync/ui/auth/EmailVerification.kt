package com.saefulrdevs.lifesync.ui.auth

import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.saefulrdevs.lifesync.R
import com.saefulrdevs.lifesync.databinding.FragmentEmailVerificationBinding
import com.saefulrdevs.lifesync.utils.CodeInputUtils
import com.saefulrdevs.lifesync.utils.EmailUtils
import com.saefulrdevs.lifesync.utils.SendMail

class EmailVerification : Fragment() {

    private var _binding: FragmentEmailVerificationBinding? = null
    private val binding get() = _binding!!
    private lateinit var countDownTimer: CountDownTimer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEmailVerificationBinding.inflate(inflater, container, false)

        val navController = findNavController()

        val isSendSuccess = arguments?.getBoolean("isSendSuccess") ?: false
        val email = arguments?.getString("email") ?: ""
        val userId = arguments?.getString("userId")

        val bundle = Bundle().apply {
            putString("email", email)
            putBoolean("isSendSuccess", isSendSuccess)
            putString("userId", userId)
        }

        Log.d("User Id Email", "User ID: $userId")

        if (isSendSuccess) {
            startCountdown()
        } else {
            binding.textViewDescription.text = "Gagal mengirim email. Silakan coba lagi."
        }

        binding.btnSubmit.setOnClickListener {
            val enteredCode =
                binding.pin1.text.toString() + binding.pin2.text.toString() + binding.pin3.text.toString() + binding.pin4.text.toString()

            val sharedPreferences =
                requireContext().getSharedPreferences("VerificationPrefs", Context.MODE_PRIVATE)
            val savedCode = sharedPreferences.getString("verificationCode", "")
            val expiryTime = sharedPreferences.getLong("expiryTime", 0)

            if (System.currentTimeMillis() > expiryTime) {
                Toast.makeText(context, "Verification code expired!", Toast.LENGTH_SHORT).show()
            } else if (enteredCode == savedCode) {
                Toast.makeText(context, "Verification successful!", Toast.LENGTH_SHORT).show()
                navController.navigate(R.id.pinFragment, bundle)
            } else {
                Toast.makeText(context, "Invalid verification code", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnResend.setOnClickListener {
            binding.progressIndicator.visibility = View.VISIBLE
            resendVerificationCode(email)
        }

        CodeInputUtils.setupPinInputListeners(
            listOf(binding.pin1, binding.pin2, binding.pin3, binding.pin4)
        )

        return binding.root
    }

    private fun startCountdown() {
        val sharedPreferences =
            requireContext().getSharedPreferences("VerificationPrefs", Context.MODE_PRIVATE)
        val expiryTime = sharedPreferences.getLong("expiryTime", 0)
        val currentTime = System.currentTimeMillis()
        val timeLeft = expiryTime - currentTime

        binding.btnResend.isEnabled = false

        countDownTimer = object : CountDownTimer(timeLeft, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                if (_binding != null) {
                    val secondsRemaining = millisUntilFinished / 1000
                    binding.textViewDescription.text =
                        "Enter the code we sent to your email (expires in $secondsRemaining seconds)"
                }
            }

            override fun onFinish() {
                if (_binding != null) {
                    binding.textViewDescription.text = "Verification code expired!"
                    binding.btnResend.isEnabled = true
                }
            }
        }
        countDownTimer.start()
    }

    private fun resendVerificationCode(email: String) {
        val newVerificationCode = EmailUtils.generateVerificationCode()

        EmailUtils.sendVerificationEmail(
            requireContext(),
            email,
            newVerificationCode,
            object : SendMail.OnMailSendListener {
                override fun onSuccess() {
                    Toast.makeText(
                        requireContext(),
                        "Email berhasil dikirim!",
                        Toast.LENGTH_SHORT
                    ).show()
                    EmailUtils.saveVerificationCodeToSharedPreferences(
                        requireContext(),
                        newVerificationCode
                    )
                    binding.progressIndicator.visibility = View.GONE
                    startCountdown()
                }

                override fun onFailure() {
                    Toast.makeText(
                        requireContext(),
                        "Gagal mengirim email. Silakan coba lagi.",
                        Toast.LENGTH_SHORT
                    ).show()

                    binding.textViewDescription.text =
                        "Gagal mengirim email. Silakan coba lagi."
                }
            })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        if (::countDownTimer.isInitialized) {
            countDownTimer.cancel()
        }
    }
}
