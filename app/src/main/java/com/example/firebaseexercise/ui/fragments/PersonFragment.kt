package com.example.firebaseexercise.ui.fragments

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firebaseexercise.R
import com.example.firebaseexercise.databinding.FragmentPersonBinding
import com.example.firebaseexercise.ui.adapters.PersonAdapter
import com.example.firebaseexercise.ui.viewmodels.PersonViewModel
import com.example.firebaseexercise.ui.viewmodels.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PersonFragment : Fragment() {

    private lateinit var binding: FragmentPersonBinding
    val viewModel: PersonViewModel by viewModels()
    val userViewModel: UserViewModel by viewModels()
    private lateinit var personAdapter: PersonAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPersonBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    showExitDialog()
                }
            })
        binding.rvUserList.apply {
            personAdapter = PersonAdapter(viewModel)
            adapter = personAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }

        viewModel.personList.observe(requireActivity(), Observer { result ->

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
        binding.logOut.setOnClickListener {
            userViewModel.logOut(requireActivity())
            findNavController().navigate(R.id.action_personFragment_to_anaSayfaFragment)

        }

    }

    private fun showExitDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Çıkış Yap")
            .setMessage("Uygulamadan çıkmak istediğinize emin misiniz?")
            .setPositiveButton("Evet") { _, _ ->
                requireActivity().finish() // Uygulamayı kapat
            }
            .setNegativeButton("Hayır") { dialog, _ ->
                dialog.dismiss() // Diyaloğu kapat
            }
            .show()

    }
}