package com.saefulrdevs.lifesync.ui.main

import android.os.Bundle
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.saefulrdevs.lifesync.R
import com.saefulrdevs.lifesync.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var areActionsVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fabMain = binding.fabMain
        val fabAddGroup = binding.fabAction1
        val fabAddTask = binding.fabAction2

        val navView: BottomNavigationView = binding.navView

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_home) as NavHostFragment
        val navController = navHostFragment.navController

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.navigation_home -> fabMain.show()
                else -> {
                    fabMain.hide()
                    hideActions(fabAddGroup, fabAddTask)
                    areActionsVisible = false
                }
            }
        }

        fabMain.setOnClickListener {
            if (areActionsVisible) {
                hideActions(fabAddGroup, fabAddTask)
            } else {
                showActions(fabAddGroup, fabAddTask)
            }
            areActionsVisible = !areActionsVisible
        }

        fabAddGroup.setOnClickListener {
            navController.navigate(R.id.add_task_group)
        }

        fabAddTask.setOnClickListener {
            navController.navigate(R.id.add_task)
        }

        navView.setupWithNavController(navController)
    }

    private fun showActions(
        fabAddGroup: ExtendedFloatingActionButton,
        fabAddTask: ExtendedFloatingActionButton
    ) {
        fabAddGroup.visibility = View.VISIBLE
        fabAddTask.visibility = View.VISIBLE
        fabAddGroup.animate()
            .translationY(-resources.getDimension(R.dimen.fab_action1_translation_y)).start()
        fabAddTask.animate()
            .translationY(-resources.getDimension(R.dimen.fab_action2_translation_y)).start()
    }

    private fun hideActions(
        fabAddGroup: ExtendedFloatingActionButton,
        fabAddTask: ExtendedFloatingActionButton
    ) {
        fabAddGroup.animate().translationY(0f).withEndAction {
            fabAddGroup.visibility = View.GONE
        }.start()
        fabAddTask.animate().translationY(0f).withEndAction {
            fabAddTask.visibility = View.GONE
        }.start()
    }
}

