package com.example.chatapp.other

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.databinding.ItemRoomBinding
import com.example.chatapp.model.Room

class RoomsAdapter(private var rooms: List<Room>?) :
    RecyclerView.Adapter<RoomsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemRoomBinding =
            ItemRoomBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(rooms!![position])
        holder.itemView.setOnClickListener {
            onItemClickListener?.onItemClick(position, rooms!![position])
        }
    }

    override fun getItemCount(): Int = rooms?.size ?: 0

    var onItemClickListener :OnItemClickListener?=null
    interface OnItemClickListener{
        fun onItemClick(position: Int, room: Room?)
    }

    fun changeData(rooms: List<Room>) {
        this.rooms = rooms
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: ItemRoomBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(room: Room?) {
            binding.item = room
            binding.invalidateAll()
        }

    }
}