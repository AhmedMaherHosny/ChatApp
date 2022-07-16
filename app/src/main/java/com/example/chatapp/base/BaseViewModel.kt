package com.example.chatapp.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel<N> : ViewModel() {
    var msgLiveData = MutableLiveData<String>()
    var msgvLiveData = MutableLiveData<String>()
    var showLoading = MutableLiveData<Boolean>()
    var navigator: N? = null
}