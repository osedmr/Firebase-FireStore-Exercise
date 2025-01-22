package com.example.firebaseexercise.data.datasource

import com.example.firebaseexercise.util.Resource
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.UserProfileChangeRequest

import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserDataSource @Inject constructor (val userAuth: FirebaseAuth) {

    val currentUser: FirebaseUser?
        get() = userAuth.currentUser


    suspend fun login(email: String, password: String):Resource<FirebaseUser>{
        return try {
            val result = userAuth.signInWithEmailAndPassword(email,password).await()
            result.user?.let {user ->
                if (user.isEmailVerified){
                    Resource.Success(user)
                }else{
                    userAuth.signOut()
                }
            }
            Resource.Success(result.user!!)
        }catch (e:Exception) {
            Resource.Error(e.localizedMessage ?: "Error")
        }
    }


    suspend fun register(name:String,email: String, password: String):Resource<FirebaseUser>{
        return try {
            val result = userAuth.createUserWithEmailAndPassword(email,password).await()
            result.user?.let { user ->
                user.sendEmailVerification().await()
                user.updateProfile(UserProfileChangeRequest.Builder().setDisplayName(name).build()).await()
                userAuth.signOut()
            }
            Resource.Success(result.user!!)
        }catch (e:Exception) {
            Resource.Error(e.localizedMessage ?: "Error")
        }
    }

    fun logOut() {
         userAuth.signOut()
    }

    // DataSource: Firebase ile giriş işlemi
    suspend fun firebaseAuthWithGoogle(account: GoogleSignInAccount): Resource<FirebaseUser> {
        return try {
            val credential: AuthCredential = GoogleAuthProvider.getCredential(account.idToken, null)
            val authResult = userAuth.signInWithCredential(credential).await()
            Resource.Success(authResult.user!!)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error("Google ile giriş başarısız: ${e.message ?: "Bilinmeyen hata"}")
        }
    }


}