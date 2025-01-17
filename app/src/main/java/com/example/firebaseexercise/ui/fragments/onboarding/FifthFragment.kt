package com.example.firebaseexercise.ui.fragments.onboarding

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.firebaseexercise.R
import com.example.firebaseexercise.databinding.FragmentFifthBinding
import com.example.firebaseexercise.ui.fragments.login.AnaSayfaFragment


class FifthFragment : Fragment() {
    private lateinit var binding: FragmentFifthBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFifthBinding.inflate(inflater,container,false)
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.right.setOnClickListener {
            onBoardScreenSeen()
            requireActivity().supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragmentContainerView, AnaSayfaFragment())
                commit()
            }
        }
        binding.left.setOnClickListener {
            OnboardingFragment.viewPagerObject?.currentItem = 3
        }
    }

    private fun onBoardScreenSeen(){
        val sharePref = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        val editor = sharePref.edit()
        editor.putBoolean("seen",true)
        editor.apply()
    }
}