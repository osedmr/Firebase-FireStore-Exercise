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
import com.google.firebase.auth.FirebaseAuth
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

// Firebase Auth üzerinden giriş yapmış kullanıcıyı kontrol et
        val currentUser = FirebaseAuth.getInstance().currentUser

        navGraph.setStartDestination(
            when {
                currentUser != null -> R.id.personFragment // Eğer kullanıcı giriş yaptıysa ana sayfaya gitsin
                seen -> R.id.anaSayfaFragment // Onboarding tamamlandıysa login sayfasına gitsin
                else -> R.id.onboardingFragment // İlk defa açıyorsa onboarding görsün
            }
        )

        navController.graph = navGraph


    }

}