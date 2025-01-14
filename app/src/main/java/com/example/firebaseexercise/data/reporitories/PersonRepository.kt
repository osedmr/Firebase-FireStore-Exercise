package com.example.firebaseexercise.data.reporitories

import androidx.lifecycle.MutableLiveData
import com.example.firebaseexercise.data.datasource.PersonDataSource
import com.example.firebaseexercise.data.entity.Person
import javax.inject.Inject

class PersonRepository @Inject constructor(val personDataSource: PersonDataSource)  {


    fun save(person: Person) =personDataSource.save(person)
    fun retrievePerson() : MutableLiveData<Result<List<Person>>> = personDataSource.retrievePerson()
    fun update(id:String,name:String,surname:String,age:Int) =personDataSource.update(id,name,surname,age)
    fun sil(id:String) = personDataSource.sil(id)
}