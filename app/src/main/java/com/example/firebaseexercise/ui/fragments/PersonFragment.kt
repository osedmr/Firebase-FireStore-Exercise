package com.example.firebaseexercise.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firebaseexercise.R
import com.example.firebaseexercise.databinding.FragmentPersonBinding
import com.example.firebaseexercise.ui.adapters.PersonAdapter
import com.example.firebaseexercise.ui.viewmodels.PersonViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PersonFragment : Fragment() {

    private lateinit var binding: FragmentPersonBinding
    val viewModel: PersonViewModel by viewModels()
    private lateinit var personAdapter: PersonAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =FragmentPersonBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.rvUserList.apply {
            personAdapter = PersonAdapter(viewModel)
            adapter=personAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
        }

        viewModel.personList.observe(requireActivity(), Observer {result ->

            result.onSuccess {
              personAdapter.differ.submitList(it)
            }
            result.onFailure {
                it.printStackTrace()
            }
        })

        binding.imageView.setOnClickListener {
            findNavController().navigate(R.id.action_personFragment_to_homeFragment)
        }

    }

}