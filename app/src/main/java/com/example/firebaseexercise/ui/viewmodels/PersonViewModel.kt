package com.example.firebaseexercise.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.firebaseexercise.data.entity.Person
import com.example.firebaseexercise.data.reporitories.PersonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PersonViewModel @Inject constructor(val personRepository: PersonRepository) : ViewModel() {

    var personList = MutableLiveData<Result<List<Person>>>()

    init {
        retrievePerson()
    }
    private fun retrievePerson(){
        personList = personRepository.retrievePerson()
    }
    fun delete(id:String){
        personRepository.sil(id)
    }
}