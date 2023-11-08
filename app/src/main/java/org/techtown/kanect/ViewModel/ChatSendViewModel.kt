package org.techtown.kanect.ViewModel

import androidx.lifecycle.ViewModel
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import org.techtown.kanect.Data.ChatMessage

class ChatSendViewModel : ViewModel() {

    private var chatRef  = Firebase.database.getReference("chat")

    fun sendChatMessage(message : ChatMessage , cafeName : String){
        chatRef.child(cafeName).push().setValue(message)
    }

}