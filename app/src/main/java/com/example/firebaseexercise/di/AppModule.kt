package com.example.firebaseexercise.di

import com.example.firebaseexercise.data.datasource.PersonDataSource
import com.example.firebaseexercise.data.reporitories.PersonRepository
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideCollectionReference(): CollectionReference {
        return FirebaseFirestore.getInstance().collection("Persons")
    }

    @Provides
    @Singleton
    fun providePersonDataSource(collectionReference: CollectionReference): PersonDataSource {
        return PersonDataSource(collectionReference)
    }

    @Provides
    @Singleton
    fun providePersonRepository(personDataSource: PersonDataSource): PersonRepository {
        return PersonRepository(personDataSource)
    }

}