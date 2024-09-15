package com.saefulrdevs.lifesync.ui.auth

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.fragment.NavHostFragment
import com.saefulrdevs.lifesync.R
import com.saefulrdevs.lifesync.databinding.ActivityVerificationBinding

class VerificationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVerificationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityVerificationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val email = intent.extras?.getString("email")
        val isSendSuccess = intent.extras?.getBoolean("isSendSuccess") ?: false

        Log.e("VerificationActivity", "Email: $email")
        Log.e("VerificationActivity", "isSendSuccess: $isSendSuccess")

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_verification) as NavHostFragment
        val navController = navHostFragment.navController

        val bundle = Bundle().apply {
            putString("email", email)
            putBoolean("isSendSuccess", isSendSuccess)
        }

        navController.navigate(R.id.verificationEmailFragment, bundle)
    }
}