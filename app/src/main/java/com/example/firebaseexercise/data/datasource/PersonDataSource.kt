package com.example.firebaseexercise.data.datasource

import androidx.lifecycle.MutableLiveData
import com.example.firebaseexercise.data.entity.Person
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class PersonDataSource @Inject constructor() {
    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    
    var kisilerListesi = MutableLiveData<Result<List<Person>>>()

    private fun getCurrentUserId(): String? = auth.currentUser?.uid

    private fun getUserCollection() = getCurrentUserId()?.let { userId ->
        firestore.collection("users").document(userId).collection("persons")
    }

    fun retrievePerson(): MutableLiveData<Result<List<Person>>> {
        getUserCollection()?.addSnapshotListener { value, error ->
            if (value != null) {
                val liste = ArrayList<Person>()
                for (d in value.documents) {
                    val kisi = d.toObject(Person::class.java)
                    if (kisi != null) {
                        kisi.id = d.id
                        liste.add(kisi)
                    }
                }
                kisilerListesi.value = Result.success(liste)
            }
        }
        return kisilerListesi
    }

    fun save(person: Person) {
        getUserCollection()?.add(person)
    }

    fun update(id: String, name: String, surname: String, age: Int) {
        val updatePerson = hashMapOf<String, Any>()
        updatePerson["name"] = name
        updatePerson["surname"] = surname
        updatePerson["age"] = age
        getUserCollection()?.document(id)?.update(updatePerson)
    }

    fun sil(id: String) {
        getUserCollection()?.document(id)?.delete()
    }
}