package com.saefulrdevs.lifesync.ui.auth

import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.saefulrdevs.lifesync.R
import com.saefulrdevs.lifesync.databinding.FragmentEmailVerificationBinding
import com.saefulrdevs.lifesync.utils.EmailUtils

class EmailVerification : Fragment() {

    private var _binding: FragmentEmailVerificationBinding? = null
    private val binding get() = _binding!!
    private lateinit var countDownTimer: CountDownTimer


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEmailVerificationBinding.inflate(inflater, container, false)

        startCountdown()

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
            } else {
                Toast.makeText(context, "Invalid verification code", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnResend.setOnClickListener {
            val email = arguments?.getString("email") ?: ""

            // Generate kode verifikasi baru
            val newVerificationCode = EmailUtils.generateVerificationCode()

            // Simpan kode baru di SharedPreferences
            EmailUtils.saveVerificationCodeToSharedPreferences(
                requireContext(),
                newVerificationCode
            )

            // Kirim ulang kode ke email
            EmailUtils.sendVerificationEmail(email, newVerificationCode)

            // Restart timer
            startCountdown()
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
        countDownTimer.cancel()
    }
}