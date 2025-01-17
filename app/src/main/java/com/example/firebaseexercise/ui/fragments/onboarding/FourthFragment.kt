package com.example.firebaseexercise.ui.fragments.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.firebaseexercise.R
import com.example.firebaseexercise.databinding.FragmentFourthBinding


class FourthFragment : Fragment() {

    private lateinit var binding: FragmentFourthBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFourthBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.right.setOnClickListener {
            OnboardingFragment.viewPagerObject?.currentItem = 4
        }

        binding.left.setOnClickListener {
            OnboardingFragment.viewPagerObject?.currentItem = 2
        }
    }
}