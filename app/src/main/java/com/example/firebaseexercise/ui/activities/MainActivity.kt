package com.example.firebaseexercise.ui.activities

import android.content.Context
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.firebaseexercise.R
import com.example.firebaseexercise.databinding.ActivityMainBinding
import com.example.firebaseexercise.ui.fragments.login.AnaSayfaFragment
import com.example.firebaseexercise.ui.fragments.onboarding.OnboardingFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)





        val sharedPref = getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        val seen = sharedPref.getBoolean("seen", false)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController

        val navGraph = navController.navInflater.inflate(R.navigation.navigation)

        navGraph.setStartDestination(
            if (seen) R.id.anaSayfaFragment else R.id.onboardingFragment
        )

        navController.graph = navGraph

    }

}