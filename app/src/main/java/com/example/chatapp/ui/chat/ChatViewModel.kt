package com.example.chatapp.ui.chat

import androidx.databinding.ObservableField
import com.example.chatapp.base.BaseViewModel
import com.example.chatapp.database.addMsg
import com.example.chatapp.model.Message
import com.example.chatapp.model.Room
import com.example.chatapp.other.DataUtils
import java.util.*

class ChatViewModel : BaseViewModel<Navigator>() {
    val msgField = ObservableField<String>()
    var room: Room? = null

    fun sndMsg() {
        val msg = Message(
            content = msgField.get()!!.trim(),
            roomID = room?.id,
            senderId = DataUtils.user?.id,
            senderName = DataUtils.user?.userName,
            dateTime = Date().time,
        )
        when (msg.content.isNullOrBlank()) {
            false -> {
                addMsg(msg, {
                    msgField.set("")
                }, {
                    msgLiveData.postValue(it.localizedMessage)
                })
            }
            else -> {}
        }
    }
}