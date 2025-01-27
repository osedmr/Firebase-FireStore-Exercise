package com.example.firebaseexercise.di

import com.example.firebaseexercise.data.datasource.PersonDataSource
import com.example.firebaseexercise.data.datasource.UserDataSource
import com.example.firebaseexercise.data.reporitories.PersonRepository
import com.example.firebaseexercise.data.reporitories.UserRepository
import com.google.firebase.auth.FirebaseAuth
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

    //person Dependency Injection
    @Provides
    @Singleton
    fun providePersonDataSource() = PersonDataSource()

    @Provides
    @Singleton
    fun providePersonRepository(personDataSource: PersonDataSource): PersonRepository {
        return PersonRepository(personDataSource)
    }

    //user Dependency Injection
    @Provides
    @Singleton
    fun provideUserAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()

    }

    @Provides
    @Singleton
    fun provideUserDataSource(userAuth: FirebaseAuth): UserDataSource {
        return UserDataSource(userAuth)
    }

    @Provides
    @Singleton
    fun provideUserRepository(userDataSource: UserDataSource): UserRepository {
        return UserRepository(userDataSource)
    }
}