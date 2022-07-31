package com.example.chatapp.ui.register


import android.util.Patterns
import androidx.databinding.ObservableField
import com.example.chatapp.base.BaseViewModel
import com.example.chatapp.database.addUserToFirestore
import com.example.chatapp.model.AppUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class RegisterViewModel : BaseViewModel<Navigator>() {
    var userName = ObservableField<String>()
    var email = ObservableField<String>()
    var password = ObservableField<String>()
    var phoneNumber = ObservableField<String>()
    var userNameError = ObservableField<String>()
    var emailError = ObservableField<String>()
    var passwordError = ObservableField<String>()
    var phoneNumberError = ObservableField<String>()
    private val auth = Firebase.auth
    //fun CharSequence?.isValidEmail() = !email.get().isNullOrBlank() && Patterns.EMAIL_ADDRESS.matcher(email.get()).matches()

    @JvmName("isValidEmail1")
    fun isValidEmail(Email: CharSequence?): Boolean {
        return !Email.isNullOrBlank() && Patterns.EMAIL_ADDRESS.matcher(email.get()!!).matches()
    }

    fun createAccount() {
        if (validation()) {
            addAccountToFirebase()
        }
    }

    private fun addAccountToFirebase() {
        showLoading.postValue(true)
        auth.createUserWithEmailAndPassword(email.get()!!, password.get()!!).addOnCompleteListener {
            showLoading.postValue(false)
            if (it.isSuccessful) {
                addInformation(it.result.user?.uid)
            } else {
                msgLiveData.postValue(it.exception?.localizedMessage.toString())
            }
        }
    }

    private fun addInformation(uid: String?) {
        showLoading.postValue(true)
        val user = AppUser(
            id = uid,
            userName = userName.get().toString(),
            email = email.get().toString(),
            password = password.get().toString(),
            phoneNumber = phoneNumber.get().toString()
        )
        addUserToFirestore(user, {
            showLoading.postValue(false)
            verifyEmail()
            FirebaseAuth.getInstance().signOut()
        }, {
            showLoading.postValue(false)
            msgLiveData.postValue(it.localizedMessage?.toString() ?: "none")
        })
    }

    private fun verifyEmail() {
        val userv = auth.currentUser
        userv!!.sendEmailVerification().addOnSuccessListener {
            msgvLiveData.postValue("Verification email has been sent check your email")
        }
            .addOnFailureListener { e ->
                msgLiveData.postValue(e.localizedMessage!!.toString())
            }
    }

    private fun validation(): Boolean {
        var valid = true
        if (userName.get().isNullOrBlank()) {
            userNameError.set("please enter your user name")
            valid = false
        } else {
            userNameError.set(null)
        }
        if (!isValidEmail(email.get())) {
            emailError.set("enter a real email")
            valid = false
        } else {
            emailError.set(null)
        }
        if (password.get().isNullOrBlank()) {
            passwordError.set("please enter your password")
            valid = false
        } else {
            passwordError.set(null)
        }
        if (phoneNumber.get().isNullOrBlank()) {
            phoneNumberError.set("please enter your phone number")
            valid = false
        } else {
            phoneNumberError.set(null)
        }
        return valid
    }

}