package com.example.chatapp.ui.login

import androidx.databinding.ObservableField
import com.example.chatapp.base.BaseViewModel
import com.example.chatapp.database.signIn
import com.example.chatapp.model.AppUser
import com.example.chatapp.other.DataUtils
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginViewModel : BaseViewModel<Navigator>() {
    private lateinit var appUser:AppUser
    var email = ObservableField<String>()
    var password = ObservableField<String>()
    val auth = Firebase.auth

    fun login() {
        authWithFirebase()
    }

    private fun authWithFirebase() {
        showLoading.postValue(true)
        auth.signInWithEmailAndPassword(email.get().toString(), password.get().toString())
            .addOnCompleteListener {
                showLoading.postValue(false)
                if (it.isSuccessful) {
                    checkUserInFirestore(it.result.user?.uid)
                } else {
                    msgLiveData.postValue("Please enter a valid email and password")
                }
            }

    }

    private fun checkUserInFirestore(uid: String?) {
        showLoading.postValue(true)
        signIn(uid!!, {
            appUser = it.toObject(AppUser::class.java)!!
            showLoading.postValue(false)
            if (it.exists()) {
                checkIfVerifiedEmail()
            } else {
                msgLiveData.postValue("Please enter a valid email and password")
            }
        }, {
            showLoading.postValue(false)
            msgLiveData.postValue(it.localizedMessage?.toString() ?: "none")
        })
    }

    private fun checkIfVerifiedEmail() {
        val userr = auth.currentUser
        if (userr!!.isEmailVerified) {
            DataUtils.user = appUser
            navigator?.openHomeScreen()
        }
        else
            navigator?.openVerificationScreen()
    }

    fun createAccount() {
        navigator?.openRegisterScreen()
    }

}