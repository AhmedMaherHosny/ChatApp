package com.example.chatapp.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.chatapp.R
import com.example.chatapp.base.BaseActivity
import com.example.chatapp.database.getAllRooms
import com.example.chatapp.databinding.ActivityHomeBinding
import com.example.chatapp.model.Room
import com.example.chatapp.other.Constants
import com.example.chatapp.other.DataUtils
import com.example.chatapp.other.RoomsAdapter
import com.example.chatapp.ui.addRoom.AddRoomActivity
import com.example.chatapp.ui.chat.ChatActivity

class HomeActivity : BaseActivity<HomeViewModel, ActivityHomeBinding>(), Navigator {
    private val adapter = RoomsAdapter(null)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel
        viewModel.navigator = this
        initRecyclerView()
    }

    private fun initRecyclerView(){
        adapter.onItemClickListener = object : RoomsAdapter.OnItemClickListener {
            override fun onItemClick(position: Int, room: Room?) {
                startChatActivity(room)
            }
        }
        binding.recyclerView.adapter = adapter
    }

    private fun startChatActivity(room: Room?) {
        val intent = Intent(this, ChatActivity::class.java)
        intent.putExtra(Constants.EXTRA_ROOM,room)
        startActivity(intent)
    }

    override fun onStart() {
        super.onStart()
        getAllRooms({
            val rooms = it.toObjects(Room::class.java)
            adapter.changeData(rooms)
        }, {
            Toast.makeText(this, it.localizedMessage, Toast.LENGTH_LONG).show()
        })
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_home
    }

    override fun initViewModel(): HomeViewModel {
        return ViewModelProvider(this)[HomeViewModel::class.java]
    }

    override fun addRoom() {
        val intent = Intent(this, AddRoomActivity::class.java)
        startActivity(intent)
    }
}