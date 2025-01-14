package com.example.firebaseexercise.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebaseexercise.data.entity.Person
import com.example.firebaseexercise.data.reporitories.PersonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(val personRepository: PersonRepository) : ViewModel()  {

    private val _saveStatus =MutableLiveData<Result<String>>()
    val saveStatus: MutableLiveData<Result<String>>
        get() = _saveStatus


    fun save(person: Person) =viewModelScope.launch(Dispatchers.IO) {
        try {
            personRepository.save(person)
            _saveStatus.postValue(Result.success("Person saved successfully"))
        }catch (e:Exception){
            e.printStackTrace()
            _saveStatus.postValue(Result.failure(e))
        }
    }
}