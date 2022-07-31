package com.example.chatapp.base

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.chatapp.ui.login.LoginActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder


abstract class BaseActivity<VM : BaseViewModel<*>, DB : ViewDataBinding> : AppCompatActivity() {
    lateinit var viewModel: VM
    lateinit var binding: DB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, getLayoutId())
        viewModel = initViewModel()
        subscribeToLiveData()
    }

    fun subscribeToLiveData() {
        viewModel.msgLiveData.observe(this) {
            showAlertDialog(it)
        }
        viewModel.msgvLiveData.observe(this) {
            showAlertDialogCustom(it)
        }
        viewModel.msgvvLiveData.observe(this) {
            showAlertDialogCustom1(it)
        }
        viewModel.showLoading.observe(this) {
            if (it == true)
                showLoading()
            else
                hideLoading()
        }
    }

    private fun showAlertDialog(message: String) {
        MaterialAlertDialogBuilder(this).setMessage(message).setCancelable(false)
            .setPositiveButton("OK") { dialog, which ->
                dialog.dismiss()
            }.show()
    }

    private fun showAlertDialogCustom(message: String) {
        MaterialAlertDialogBuilder(this).setMessage(message).setCancelable(false)
            .setPositiveButton("OK") { dialog, which ->
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                dialog.dismiss()
            }.show()
    }

    private fun showAlertDialogCustom1(message: String) {
        MaterialAlertDialogBuilder(this).setMessage(message).setCancelable(false)
            .setPositiveButton("OK") { dialog, which ->
//                val intent = Intent(this, HomeActivity::class.java)
//                startActivity(intent)
                dialog.dismiss()
                finish()
            }.show()
    }

    private var progressDialog: ProgressDialog? = null
    private fun showLoading() {
        progressDialog = ProgressDialog(this)
        progressDialog?.setMessage("Loading...")
        progressDialog?.setCancelable(false)
        progressDialog?.show()
    }

    private fun hideLoading() {
        progressDialog?.dismiss()
        progressDialog = null
    }

    abstract fun getLayoutId(): Int
    abstract fun initViewModel(): VM
}