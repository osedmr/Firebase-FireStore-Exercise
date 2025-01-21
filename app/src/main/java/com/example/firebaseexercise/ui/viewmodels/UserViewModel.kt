package com.example.firebaseexercise.ui.viewmodels

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebaseexercise.R
import com.example.firebaseexercise.data.reporitories.UserRepository
import com.example.firebaseexercise.util.Resource
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(val userRepository: UserRepository) : ViewModel() {

    private val _loginFlow = MutableStateFlow<Resource<FirebaseUser>?>(null)
    val loginFlow: MutableStateFlow<Resource<FirebaseUser>?> = _loginFlow

    private val _registerFlow = MutableStateFlow<Resource<FirebaseUser>?>(null)
    val registerFlow: MutableStateFlow<Resource<FirebaseUser>?> = _registerFlow

    val currentUser: FirebaseUser?
        get() = userRepository.currentUser

    private val _userState = MutableLiveData<Boolean>()
    val userState: LiveData<Boolean> get() = _userState

    // UserViewModel'de
    private val _navigateToHome = MutableLiveData<Boolean>(false) // private mutable LiveData
    val navigateToHome: LiveData<Boolean> get() = _navigateToHome // public immutable LiveData
    // navigateToHome'u değiştirebilmek için bir metot ekliyoruz
    fun setNavigateToHome(value: Boolean) {
        _navigateToHome.value = value
    }

    init {
        checkUserSession()
    }
    fun login(email: String, password: String) = viewModelScope.launch(Dispatchers.IO) {
        _loginFlow.value =Resource.Loading()
        val result = userRepository.login(email, password)
        _loginFlow.value = result

    }

    fun register(name:String,email: String, password: String) = viewModelScope.launch(Dispatchers.IO) {
        _registerFlow.value =Resource.Loading()
        val result = userRepository.register(name,email, password)
        _registerFlow.value = result

    }

    fun logOut(activity: Activity) {
        userRepository.logOut()
        val googleSignInClient = getGoogleSignInClient(activity)
        googleSignInClient.signOut()
        _loginFlow.value = null
        _registerFlow.value = null

    }
    private fun checkUserSession() {
        _userState.value = currentUser != null // Kullanıcı giriş yaptı mı?
    }
    // firebaseAuthWithGoogle() fonksiyonunda yönlendirmeyi postValue() ile yapıyoruz.
    fun firebaseAuthWithGoogle(account: GoogleSignInAccount) = viewModelScope.launch(Dispatchers.IO) {
        _loginFlow.value = Resource.Loading()
        try {
            val result = userRepository.firebaseAuthWithGoogle(account)
            _loginFlow.value = result

            if (result is Resource.Success) {
                withContext(Dispatchers.Main) {
                    _navigateToHome.value = true  // Firebase auth sonrası yönlendirme
                }
            }
        } catch (e: Exception) {
            _loginFlow.value = Resource.Error("Giriş hatası: ${e.message}")
        }
    }







    fun getGoogleSignInClient(activity: Activity) : GoogleSignInClient {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(activity.getString(R.string.web_client_id))
            .requestEmail()
            .build()
        return GoogleSignIn.getClient(activity,gso)
    }

}