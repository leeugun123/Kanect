package org.techtown.kanect

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Adapter
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.kakao.sdk.user.UserApiClient
import org.techtown.kanect.Adapter.ChatAdapter
import org.techtown.kanect.Data.ChatMessage
import org.techtown.kanect.databinding.ActivityChatBinding
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class ChatActivity : AppCompatActivity() {

    private lateinit var binding : ActivityChatBinding
    private lateinit var chatAdapter : ChatAdapter
    private val messages: MutableList<ChatMessage> = mutableListOf()
    // Firebase Realtime Database
    private val database = FirebaseDatabase.getInstance()

    private var userImg : String = ""
    private var userName : String = ""
    private var userId : Long = 0

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val cafeName : String = intent.getStringExtra("cafeName").toString()
        val chatRef = database.reference.child("chat").child(cafeName)

        UserApiClient.instance.me { user, error ->

            user?.let {

                userId = it.id!!
                userImg = it.kakaoAccount?.profile?.profileImageUrl.toString()
                userName = it.kakaoAccount?.profile?.nickname.toString()
                //카카오 api에서 정보 가져오기

                chatAdapter = ChatAdapter(messages,userId)
                val layoutManager = LinearLayoutManager(this)
                binding.chatRecyclerView.layoutManager = layoutManager
                binding.chatRecyclerView.adapter = chatAdapter
                //Adapter 및 RecyclerView 연결

                chatRef.addValueEventListener(object : ValueEventListener {

                    override fun onDataChange(snapshot: DataSnapshot) {

                        messages.clear()

                        for (childSnapshot in snapshot.children) {

                            val chatMessage = childSnapshot.getValue(ChatMessage::class.java)

                            chatMessage?.let {
                                messages.add(it)
                            }

                        }

                        chatAdapter.notifyDataSetChanged()
                        binding.chatRecyclerView.scrollToPosition(messages.size - 1)

                    }

                    override fun onCancelled(error: DatabaseError) {
                        // 처리되지 않은 오류를 처리
                    }

                })
                //채팅 목록 가져오기

            }

        }



        binding.sendButton.setOnClickListener {

            val messageText = binding.messageInputEditText.text.toString()

            if(messageText.isNotBlank()){

                val message = ChatMessage(userId , userImg, userName , messageText , getCurrentTimeAsString())
                chatRef.push().setValue(message)
                binding.messageInputEditText.text.clear()

            }

        }//전송 버튼

        // Firebase Realtime Database에서 채팅 메시지 가져오기



    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getCurrentTimeAsString(): String {

        val currentTime = LocalTime.now()
        val formatter = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            DateTimeFormatter.ofPattern("a hh:mm")
        } else {
            TODO("VERSION.SDK_INT < O")
        }
        return currentTime.format(formatter)


    }



}