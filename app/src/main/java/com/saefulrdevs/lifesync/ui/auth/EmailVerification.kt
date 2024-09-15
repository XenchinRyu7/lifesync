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
        Log.e("EmailVerification", "Email: $email")
        Log.e("EmailVerification", "isSendSuccess: $isSendSuccess")

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
                navController.navigate(R.id.pinFragment)
            } else {
                Toast.makeText(context, "Invalid verification code", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnResend.setOnClickListener {

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

        return binding.root
    }

    private fun startCountdown() {
        val sharedPreferences =
            requireContext().getSharedPreferences("VerificationPrefs", Context.MODE_PRIVATE)
        val expiryTime = sharedPreferences.getLong("expiryTime", 0)
        val currentTime = System.currentTimeMillis()
        val timeLeft = expiryTime - currentTime

        countDownTimer = object : CountDownTimer(timeLeft, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsRemaining = millisUntilFinished / 1000
                binding.textViewDescription.text =
                    "Enter the code we sent to your email (expires in $secondsRemaining seconds)"
            }

            override fun onFinish() {
                binding.textViewDescription.text = "Verification code expired!"
            }
        }
        countDownTimer.start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        if (::countDownTimer.isInitialized) {
            countDownTimer.cancel()
        }
    }
}