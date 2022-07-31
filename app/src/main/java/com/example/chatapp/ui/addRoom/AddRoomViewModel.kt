package com.example.chatapp.ui.addRoom

import androidx.databinding.ObservableField
import com.example.chatapp.base.BaseViewModel
import com.example.chatapp.database.addRoom
import com.example.chatapp.model.Room
import com.example.chatapp.model.SpinnerCategory


class AddRoomViewModel : BaseViewModel<Navigator>() {
    var roomName = ObservableField<String>()
    var roomNameError = ObservableField<String>()
    var roomDescription = ObservableField<String>()
    var roomDescriptionError = ObservableField<String>()
    val spinnerCategoryList = SpinnerCategory.getSpinnerCategoriesList()
    var selectedItem = spinnerCategoryList[0]

    fun createRoom() {
        when {
            validation() -> {
                val room = Room(
                    name = roomName.get().toString(),
                    description = roomDescription.get().toString(),
                    categoryId = selectedItem.id,
                )
                showLoading.postValue(true)
                addRoom(room, {
                    showLoading.postValue(false)
                    msgvvLiveData.postValue("The Room is Added Successfully")
                }, {
                    showLoading.postValue(false)
                    msgLiveData.postValue(it.localizedMessage)
                })
            }
        }
    }


    private fun validation(): Boolean {
        var valid = true
        if (roomName.get().isNullOrBlank()) {
            roomNameError.set("Please enter Room Name")
            valid = false
        } else {
            roomNameError.set(null)
        }
        if (roomDescription.get().isNullOrBlank()) {
            roomDescriptionError.set("Please enter Room Description")
            valid = false
        } else {
            roomDescriptionError.set(null)
        }
        return valid
    }

}