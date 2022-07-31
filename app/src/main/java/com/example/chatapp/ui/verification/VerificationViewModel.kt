package com.example.chatapp.ui.verification

import com.example.chatapp.base.BaseViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class VerificationViewModel : BaseViewModel<Navigator>() {
    val auth = Firebase.auth
    fun verifyNow() {
        val userv = auth.currentUser
        userv!!.sendEmailVerification().addOnSuccessListener {
            msgvLiveData.postValue("Verification email has been sent check your email")
        }
            .addOnFailureListener { e ->
                msgLiveData.postValue(e.localizedMessage!!.toString())
            }
    }

}