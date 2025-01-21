package com.example.firebaseexercise.data.reporitories

import com.example.firebaseexercise.data.datasource.UserDataSource
import com.example.firebaseexercise.util.Resource
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class UserRepository @Inject constructor (val userDataSource: UserDataSource) {

    val currentUser: FirebaseUser?
        get() = userDataSource.currentUser
    suspend fun login(email: String, password: String): Resource<FirebaseUser> = userDataSource.login(email, password)
    suspend fun register(name:String,email: String, password: String): Resource<FirebaseUser> = userDataSource.register(name,email, password)
    fun logOut() = userDataSource.logOut()

    // Repository: Firebase ile giriş işlemi
    suspend fun firebaseAuthWithGoogle(account: GoogleSignInAccount): Resource<FirebaseUser> {
        return userDataSource.firebaseAuthWithGoogle(account)
    }


}