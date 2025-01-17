package com.example.firebaseexercise.ui.fragments.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.firebaseexercise.R
import com.example.firebaseexercise.databinding.FragmentOnboardingBinding
import com.example.firebaseexercise.ui.adapters.OnboardingAdapter


class OnboardingFragment : Fragment() {

    private lateinit var binding:FragmentOnboardingBinding
    private lateinit var adapter: OnboardingAdapter

    companion object{
        var viewPagerObject: ViewPager2? = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =FragmentOnboardingBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val dotsIndicator = binding.dotsIndicator
        val viewPager = binding.viewPager
        adapter = OnboardingAdapter(requireActivity().supportFragmentManager, lifecycle)
        viewPager.adapter = adapter
        dotsIndicator.attachTo(viewPager)

        viewPagerObject = binding.viewPager
    }

    override fun onDestroy() {
        super.onDestroy()
        viewPagerObject = null

    }


}