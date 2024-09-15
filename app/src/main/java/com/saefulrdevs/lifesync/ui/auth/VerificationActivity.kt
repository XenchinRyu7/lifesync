package com.saefulrdevs.lifesync.ui.auth

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.fragment.NavHostFragment
import com.saefulrdevs.lifesync.R

class VerificationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Buat FragmentContainerView untuk NavHostFragment
        val fragmentContainer = FragmentContainerView(this).apply {
            id = View.generateViewId()  // Generate ID untuk fragment container
        }
        setContentView(fragmentContainer)

        // Ambil data dari intent
        val email = intent.extras?.getString("email")

        // Atur NavHostFragment dengan NavController dan kirim data ke EmailVerificationFragment
        if (savedInstanceState == null) {
            val navHostFragment = NavHostFragment.create(R.navigation.verification_navigation)
            supportFragmentManager.beginTransaction()
                .replace(fragmentContainer.id, navHostFragment)
                .setPrimaryNavigationFragment(navHostFragment)
                .commit()

            // Setelah NavHostFragment dibuat, kirim data ke fragment pertama
            navHostFragment.navController.addOnDestinationChangedListener { _, destination, _ ->
                if (destination.id == R.id.verificationEmailFragment) {
                    val bundle = Bundle().apply {
                        putString("email", email)
                    }
                    navHostFragment.navController.setGraph(R.navigation.verification_navigation, bundle)
                }
            }
        }
    }
}

