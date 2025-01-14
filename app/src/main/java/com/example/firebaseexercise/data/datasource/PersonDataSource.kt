package com.example.firebaseexercise.data.datasource

import androidx.lifecycle.MutableLiveData
import com.example.firebaseexercise.data.entity.Person
import com.google.firebase.firestore.CollectionReference
import javax.inject.Inject


class PersonDataSource @Inject constructor(val collectionReference: CollectionReference) {


    var kisilerListesi = MutableLiveData<Result<List<Person>>>()


    fun retrievePerson() : MutableLiveData<Result<List<Person>>> {
        collectionReference.addSnapshotListener { value, error ->
            if(value != null){
                val liste = ArrayList<Person>()

                for(d in value.documents){
                    val kisi = d.toObject(Person::class.java)
                    if(kisi != null){
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
        collectionReference.add(person)

    }
    fun update(id:String,name:String,surname:String,age:Int) {
        val updatePerson = hashMapOf<String,Any>()
        updatePerson["name"] = name
        updatePerson["surname"] = surname
        updatePerson["age"] = age
        collectionReference.document(id).update(updatePerson)
    }

    fun sil(id:String){
        collectionReference.document(id).delete()
    }



}