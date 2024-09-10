package com.saefulrdevs.lifesync.ui.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import com.google.android.material.tabs.TabLayoutMediator
import com.saefulrdevs.lifesync.App
import com.saefulrdevs.lifesync.databinding.ActivityAuthBinding
import com.saefulrdevs.lifesync.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreferences = getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("hasAccessedAuth", true)
        editor.apply()

        val app = this.applicationContext as App
        val userId = app.userId

        if (userId != null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        val viewPager = binding.viewPager
        val tabLayout = binding.tabLayout

        val pagerAdapter = LoginRegisterPagerAdapter(this)
        viewPager.adapter = pagerAdapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Login"
                1 -> "Register"
                else -> null
            }
        }.attach()

    }
}