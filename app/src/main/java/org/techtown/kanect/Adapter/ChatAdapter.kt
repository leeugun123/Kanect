package org.techtown.kanect.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.techtown.kanect.Data.ChatMessage
import org.techtown.kanect.databinding.ItemChatMessageOtherBinding
import org.techtown.kanect.databinding.ItemChatMessageUserBinding

class ChatAdapter(private val messages : List<ChatMessage> , private val myId : Long) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    inner class MessageViewHolderUser(private val binding: ItemChatMessageUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val messageText = binding.chatUserText
        val timeStamp = binding.chatTimeStamp
    }

    inner class MessageViewHolderOther(private val binding: ItemChatMessageOtherBinding) :
            RecyclerView.ViewHolder(binding.root){
            val messageText = binding.chatUserText
            val userImg = binding.chatUserImg
            val userName = binding.chatUserName
            val timeStamp = binding.chatUserTimeStamp

            }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val inflater = LayoutInflater.from(parent.context)

        return if(viewType == VIEW_TYPE_USER){

            val userBinding = ItemChatMessageUserBinding.inflate(inflater,parent,false)
            MessageViewHolderUser(userBinding)

        }else{

            val otherBinding = ItemChatMessageOtherBinding.inflate(inflater,parent,false)
            MessageViewHolderOther(otherBinding)

        }

    }

    override fun onBindViewHolder(holder : RecyclerView.ViewHolder, position : Int) {

        val chatList = messages[position]

        when(holder){

            is MessageViewHolderUser -> {

                holder.messageText.text = chatList.text
                holder.timeStamp.text = chatList.timestamp

            }

            is MessageViewHolderOther -> {

                holder.messageText.text = chatList.text
                holder.timeStamp.text = chatList.timestamp
                holder.userName.text = chatList.userName

                Glide.with(holder.itemView.context)
                    .load(chatList.userImg)
                    .circleCrop()
                    .override(50,50)
                    .into(holder.userImg)

            }


        }



    }

    override fun getItemCount(): Int {
        return messages.size
    }

    override fun getItemViewType(position: Int): Int {
        // 메시지를 보낸 사용자와 현재 사용자를 비교하여 뷰 타입을 결정합니다.
        return if (messages[position].userId == myId) {
            VIEW_TYPE_USER
        } else {
            VIEW_TYPE_OTHER
        }
    }

    companion object {
        private const val VIEW_TYPE_USER = 0
        private const val VIEW_TYPE_OTHER = 1
    }

}