package org.techtown.kanect.ViewModel

import android.os.Message
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import org.techtown.kanect.Data.ChatMessage

class ChatDataViewModel : ViewModel() {

    private val _chatMessageLiveData = MutableLiveData<MutableList<ChatMessage>>()

    val chatMessageLiveData : LiveData<MutableList<ChatMessage>>
        get() = _chatMessageLiveData

    fun getChatMessage(cafeName : String){

        val chatRef = FirebaseDatabase.getInstance().reference.child("chat").child(cafeName)

        var messages: MutableList<ChatMessage> = mutableListOf()

        chatRef.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                var previousDayMessage: String? = null

                messages.clear()

                for (childSnapshot in snapshot.children) {

                    val chatMessage = childSnapshot.getValue(ChatMessage::class.java)

                    chatMessage?.let {

                        val currentDayMessage = it.dayStamp

                        if(previousDayMessage != currentDayMessage) {

                            messages.add(ChatMessage(0,"","","","",
                                currentDayMessage, true))   // 이후 현재 메시지의 날짜를 이전 메시지의 날짜로 설정

                            previousDayMessage = currentDayMessage

                        }//날짜 표를 중간마다 설정

                        messages.add(it)

                    }

                }

                _chatMessageLiveData.value = messages

            }

            override fun onCancelled(error: DatabaseError) {

            }


        })




    }



}