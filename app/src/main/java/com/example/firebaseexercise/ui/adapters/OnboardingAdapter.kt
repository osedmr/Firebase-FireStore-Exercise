package com.example.firebaseexercise.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.firebaseexercise.ui.fragments.onboarding.FifthFragment
import com.example.firebaseexercise.ui.fragments.onboarding.FirstFragment
import com.example.firebaseexercise.ui.fragments.onboarding.FourthFragment
import com.example.firebaseexercise.ui.fragments.onboarding.SecondFragment
import com.example.firebaseexercise.ui.fragments.onboarding.ThirdFragment

class OnboardingAdapter (fragment: FragmentManager, lifecycle: Lifecycle): FragmentStateAdapter(fragment,lifecycle) {

    val fragments = listOf(FirstFragment(), SecondFragment(), ThirdFragment(), FourthFragment(), FifthFragment())
    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}