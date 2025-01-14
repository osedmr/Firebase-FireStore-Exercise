package com.example.firebaseexercise.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.firebaseexercise.R
import com.example.firebaseexercise.databinding.FragmentDetailBinding
import com.example.firebaseexercise.ui.viewmodels.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    val viewModel: DetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle: DetailFragmentArgs by navArgs()
        val person = bundle.person
        binding.apply {
            etName.setText(person.name)
            etSurname.setText(person.surname)
            etAge.setText(person.age.toString())
        }
        binding.btnUpdate.setOnClickListener {
            val name = binding.etName.text.toString()
            val surname = binding.etSurname.text.toString()
            val age = binding.etAge.text.toString().toIntOrNull()
            val id = person.id
            if (name.isNotEmpty() && surname.isNotEmpty() && age != null){
                viewModel.update(id!!,name,surname,age)
                Toast.makeText(requireContext(),"Başarıyla güncellendi",Toast.LENGTH_SHORT).show()
                Log.e("güncellenen kişi","$id $name $surname $age")
            }else{
                Toast.makeText(requireContext(),"Boş alanları doldurunuz",Toast.LENGTH_SHORT).show()
            }
        }


    }



}