package com.example.chatapp.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.chatapp.R
import com.example.chatapp.database.signIn
import com.example.chatapp.model.AppUser
import com.example.chatapp.other.DataUtils
import com.example.chatapp.ui.home.HomeActivity
import com.example.chatapp.ui.login.LoginActivity
import com.example.chatapp.ui.verification.VerificationActivity
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private var firebaseUser: FirebaseUser? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler(Looper.getMainLooper())
            .postDelayed({
                checkLoggedInUser()
            }, 200)
    }

    private fun checkLoggedInUser() {
        firebaseUser = Firebase.auth.currentUser
        when (firebaseUser) {
            null -> {
                startLoginActivity()
            }
            else -> {
                startWhichActivity()
            }
        }
    }

    private fun startWhichActivity() {
        when (firebaseUser?.isEmailVerified) {
            true -> {
                signIn(firebaseUser!!.uid, {
                    val user = it.toObject(AppUser::class.java)
                    DataUtils.user = user
                    startHomeActivity()
                }, {
                    startLoginActivity()
                })
            }
            else -> {
                startVerificationActivity()
            }
        }
    }

    private fun startVerificationActivity() {
        val intent = Intent(this, VerificationActivity::class.java)
        startActivity(intent)
    }

    private fun startHomeActivity() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }

    private fun startLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}