package com.example.chatapp.ui.home

import com.example.chatapp.base.BaseViewModel

class HomeViewModel : BaseViewModel<Navigator>() {

    fun createRoom() {
        navigator?.addRoom()
    }
}