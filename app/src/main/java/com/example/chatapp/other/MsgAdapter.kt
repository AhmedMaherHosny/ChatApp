package com.example.chatapp.other

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.R
import com.example.chatapp.databinding.ItemMsgRecieveBinding
import com.example.chatapp.databinding.ItemMsgSentBinding
import com.example.chatapp.model.Message

class MsgAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var msgs = mutableListOf<Message?>()

    override fun getItemViewType(position: Int): Int {
        val msg = msgs[position]
        return when (msg?.senderId) {
            DataUtils.user?.id -> Constants.SENT
            else -> Constants.RECIEVE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            Constants.SENT -> {
                val binding: ItemMsgSentBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.item_msg_sent,
                    parent,
                    false
                )
                return SentMsgViewHolder(binding)
            }
            else -> {
                val binding: ItemMsgRecieveBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.item_msg_recieve,
                    parent,
                    false
                )
                return RecieveMsgViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is SentMsgViewHolder -> {
                holder.bind(msgs[position]!!)
            }
            is RecieveMsgViewHolder -> {
                holder.bind(msgs[position]!!)
            }
        }
    }

    override fun getItemCount(): Int = msgs.size

    fun appendMsgs(newMsgList: MutableList<Message>) {
        msgs.addAll(newMsgList)
        notifyItemRangeInserted(msgs.size + 1, newMsgList.size)

    }

    class SentMsgViewHolder(val binding: ItemMsgSentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(msg: Message) {
            binding.msg = msg
            binding.invalidateAll()
        }
    }

    class RecieveMsgViewHolder(val binding: ItemMsgRecieveBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(msg: Message) {
            binding.msg = msg
            binding.invalidateAll()
        }
    }

}