package org.techtown.kanect.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.techtown.kanect.Data.ChatMessage
import org.techtown.kanect.databinding.ItemChatDateBinding
import org.techtown.kanect.databinding.ItemChatMessageOtherBinding
import org.techtown.kanect.databinding.ItemChatMessageUserBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

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

    inner class DateViewHolder(private val binding: ItemChatDateBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val dateText = binding.dateText
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {

            VIEW_TYPE_USER -> {

                val userBinding = ItemChatMessageUserBinding.inflate(inflater, parent, false)
                MessageViewHolderUser(userBinding)

            }

            VIEW_TYPE_OTHER -> {

                val otherBinding = ItemChatMessageOtherBinding.inflate(inflater, parent, false)
                MessageViewHolderOther(otherBinding)

            }

            else -> {

                val dateBinding = ItemChatDateBinding.inflate(inflater, parent, false)
                DateViewHolder(dateBinding)

            }

        }



    }

    override fun onBindViewHolder(holder : RecyclerView.ViewHolder, position : Int) {

        val chatList = messages[position]

        when (holder.itemViewType) {

            VIEW_TYPE_USER -> {

                val userHolder = holder as MessageViewHolderUser
                userHolder.messageText.text = chatList.text
                userHolder.timeStamp.text = chatList.timestamp

            }

            VIEW_TYPE_OTHER -> {

                val otherHolder = holder as MessageViewHolderOther
                otherHolder.messageText.text = chatList.text
                otherHolder.timeStamp.text = chatList.timestamp
                otherHolder.userName.text = chatList.userName

                Glide.with(otherHolder.itemView.context)
                    .load(chatList.userImg)
                    .circleCrop()
                    .override(50, 50)
                    .into(otherHolder.userImg)

            }

            VIEW_TYPE_DATE -> {

                val dateHolder = holder as DateViewHolder
                dateHolder.dateText.text = formatDateString(chatList.dayStamp)

            }

        }

    }

    override fun getItemCount(): Int {
        return messages.size
    }

    override fun getItemViewType(position: Int): Int {

        // 메시지를 보낸 사용자와 현재 사용자를 비교하여 뷰 타입을 결정합니다.

        val currentItem = messages[position]

        //Date 변수가 true 라면
        return if (currentItem.dayMessage) {
            VIEW_TYPE_DATE
        } else if (currentItem.userId == myId) {
            VIEW_TYPE_USER
        } else {
            VIEW_TYPE_OTHER
        }

    }

    private fun formatDateString(inputDate: String): String {

        try {
            val inputFormat = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
            val outputFormat = SimpleDateFormat("yyyy년 MM월 dd일 EEEE", Locale.getDefault())

            val date = inputFormat.parse(inputDate)

            return outputFormat.format(date ?: Date())

        } catch (e: Exception) {
            e.printStackTrace()
        }
        
        return ""

    }

    companion object {

        private const val VIEW_TYPE_USER = 0
        private const val VIEW_TYPE_OTHER = 1
        private const val VIEW_TYPE_DATE = 2

    }


}