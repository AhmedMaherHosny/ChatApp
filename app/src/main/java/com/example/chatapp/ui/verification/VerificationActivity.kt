package com.example.chatapp.ui.verification

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.chatapp.R
import com.example.chatapp.base.BaseActivity
import com.example.chatapp.databinding.ActivityVerificationBinding

class VerificationActivity : BaseActivity<VerificationViewModel, ActivityVerificationBinding>(), Navigator {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel
        viewModel.navigator = this

    }

    override fun getLayoutId(): Int {
        return R.layout.activity_verification
    }

    override fun initViewModel(): VerificationViewModel {
        return ViewModelProvider(this)[VerificationViewModel::class.java]
    }
}