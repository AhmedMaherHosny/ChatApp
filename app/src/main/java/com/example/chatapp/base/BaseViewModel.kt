package com.example.chatapp.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel<N> : ViewModel() {
    var msgLiveData = MutableLiveData<String>()
    var showLoading = MutableLiveData<Boolean>()
    var msgvLiveData = MutableLiveData<String>()
    var msgvvLiveData = MutableLiveData<String>()
    var navigator: N? = null
}