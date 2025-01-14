package com.example.firebaseexercise.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebaseexercise.data.entity.Person
import com.example.firebaseexercise.data.reporitories.PersonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(val personRepository: PersonRepository) :ViewModel() {


    fun update(id:String,name:String,surname:String,age:Int){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                personRepository.update(id,name,surname,age)
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }
}