package com.example.chatapp.ui.chat

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatapp.R
import com.example.chatapp.base.BaseActivity
import com.example.chatapp.database.getMsgRef
import com.example.chatapp.databinding.ActivityChatBinding
import com.example.chatapp.model.Message
import com.example.chatapp.model.Room
import com.example.chatapp.other.Constants
import com.example.chatapp.other.MsgAdapter
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.Query

class ChatActivity : BaseActivity<ChatViewModel, ActivityChatBinding>(), Navigator {
    lateinit var room: Room
    private val msgAdapter = MsgAdapter()
    private lateinit var layoutManger: LinearLayoutManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel
        viewModel.navigator = this
        room = intent.getParcelableExtra(Constants.EXTRA_ROOM)!!
        viewModel.room = room
        initRecyclerView()
    }

    private fun initRecyclerView() {
        layoutManger = LinearLayoutManager(this).apply { stackFromEnd = true }
        binding.recyclerView.layoutManager = layoutManger
        binding.recyclerView.adapter = msgAdapter
        realTimeMsgListener()
    }

    private fun realTimeMsgListener() {
        getMsgRef(room.id!!).orderBy("dateTime", Query.Direction.ASCENDING)
            .addSnapshotListener { snapShot, ex ->
                when {
                    ex != null -> {
                        Toast.makeText(this, ex.localizedMessage, Toast.LENGTH_LONG).show()
                    }
                    else -> {
                        val newMsgList = mutableListOf<Message>()
                        snapShot!!.documentChanges.forEach { doc ->
                            if (doc.type == DocumentChange.Type.ADDED) {
                                newMsgList.add(doc.document.toObject(Message::class.java))
                            }
                        }
                        msgAdapter.appendMsgs(newMsgList)
                        binding.recyclerView.smoothScrollToPosition(msgAdapter.itemCount)
                    }
                }
            }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_chat
    }

    override fun initViewModel(): ChatViewModel {
        return ViewModelProvider(this)[ChatViewModel::class.java]
    }
}
