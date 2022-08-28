package com.example.pochackathon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.pochackathon.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
    }

    private fun setupView() {
        val fragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        val navController = (fragment as? NavHostFragment)?.navController ?: return

        binding.bottomNavigationView.setupWithNavController(navController)
    }
}
