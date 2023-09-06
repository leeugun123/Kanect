package org.techtown.kanect.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.techtown.kanect.Data.ChatMessage
import org.techtown.kanect.databinding.ItemChatMessageBinding

class ChatAdapter(private val messages : List<ChatMessage>) :
    RecyclerView.Adapter<ChatAdapter.ViewHolder>(){

   inner class ViewHolder(val binding : ItemChatMessageBinding) : RecyclerView.ViewHolder(binding.root),
            View.OnClickListener{

                init {
                    itemView.setOnClickListener(this)
                }

               override fun onClick(v : View?) {

                   val position = adapterPosition
                   val context = itemView.context

               }



            }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatAdapter.ViewHolder {

        val binding = ItemChatMessageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder : ChatAdapter.ViewHolder, position : Int) {

        val chatList = messages[position]

        Glide.with(holder.itemView.context)
            .load(chatList.userImg)
            .centerCrop()
            .override(50,50)
            .into(holder.binding.chatUserImg)

        holder.binding.chatUserName.text = chatList.userName
        holder.binding.chatUserText.text = chatList.text
        holder.binding.chatUserTimeStamp.text = chatList.timestamp

    }

    override fun getItemCount(): Int {
        return messages.size
    }

}