package org.techtown.kanect.UI.Activity

import android.app.AlertDialog
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import org.techtown.kanect.Adapter.ChatAdapter
import org.techtown.kanect.Data.ChatMessage
import org.techtown.kanect.Object.GetTime
import org.techtown.kanect.Object.UserKakaoInfo
import org.techtown.kanect.ViewModel.CafeCountViewModel
import org.techtown.kanect.ViewModel.ChatDataViewModel
import org.techtown.kanect.ViewModel.ChatSendViewModel
import org.techtown.kanect.ViewModel.UploadCafeNumViewModel
import org.techtown.kanect.databinding.ActivityChatBinding

class ChatActivity : AppCompatActivity() {

    private lateinit var binding : ActivityChatBinding
    private lateinit var chatAdapter : ChatAdapter
    private val messages: MutableList<ChatMessage> = mutableListOf()
    // Firebase Realtime Database
    private lateinit var cafeName : String

    private lateinit var cafeCountViewModel : CafeCountViewModel
    private lateinit var chatDataViewModel : ChatDataViewModel
    private lateinit var uploadCafeNumViewModel : UploadCafeNumViewModel
    private lateinit var chatSendViewModel : ChatSendViewModel

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModelInit()
        chatInit()

       uploadCafeNumViewModel.uploadCafeCount(true , cafeName)
        // 카페 인원 추가 요청

        cafeCountViewModel.getCafeNum(cafeName)

        cafeCountViewModel.cafeChatNum.observe(this) { cafeChatNum ->
            binding.chatNum.text = cafeChatNum.toString() + "명"
        }
        //카페 인원 정보 업데이트

        chatDataViewModel.getChatMessage(cafeName)

        chatDataViewModel.chatMessageLiveData.observe(this){ chatList->
            chatUpdate(chatList)
        }
        //채팅 정보 가져오기

        binding.sendButton.setOnClickListener {

            val messageText = binding.messageInputEditText.text.toString()

            if(messageText.isNotBlank()){

                chatSendViewModel.sendChatMessage(ChatMessage(
                    UserKakaoInfo.userId,
                    UserKakaoInfo.userImg,
                    UserKakaoInfo.userName,
                    messageText,
                    GetTime.getCurrentTimeAsString(),
                    GetTime.getCurrentDateAsInt(),
                    false),cafeName)

                binding.messageInputEditText.text.clear()

            }

        }//채팅 전송 버튼



    }

    private fun viewModelInit() {
        cafeCountViewModel = ViewModelProvider(this).get(CafeCountViewModel::class.java)
        chatDataViewModel = ViewModelProvider(this).get(ChatDataViewModel::class.java)
        uploadCafeNumViewModel = ViewModelProvider(this).get(UploadCafeNumViewModel::class.java)
        chatSendViewModel = ViewModelProvider(this).get(ChatSendViewModel::class.java)
    }

    private fun chatInit() {

        cafeName = intent.getStringExtra("cafeName").toString()

        Glide.with(this)
            .load(intent.getStringExtra("cafeImg"))
            .override(100,100)
            .fitCenter()
            .into(binding.cafeImg)

        binding.cafeName.text = cafeName

        chatAdapter = ChatAdapter(messages, UserKakaoInfo.userId)


        Toast.makeText(this, cafeName + "에 입장하였습니다.",Toast.LENGTH_SHORT).show()

    }

    private fun chatUpdate(chatList : MutableList<ChatMessage>){

        chatAdapter = ChatAdapter(chatList, UserKakaoInfo.userId)

        val layoutManager = LinearLayoutManager(this)
        binding.chatRecyclerView.layoutManager = layoutManager
        binding.chatRecyclerView.adapter = chatAdapter
        binding.chatRecyclerView.scrollToPosition(chatList.size - 1)
        //최근 메세지로 View 이동

    }



    private fun getOut(){

        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("채팅방 퇴장")
        alertDialogBuilder.setMessage("$cafeName 채팅방을 퇴장하시겠습니까?")

        // "예" 버튼 설정 및 클릭 리스너 추가
        alertDialogBuilder.setPositiveButton("예") { _, _ ->

            uploadCafeNumViewModel.uploadCafeCount(false,cafeName)
            finish()

        }

        alertDialogBuilder.setNegativeButton("아니요") { _, _ ->

        }

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()

    }

    override fun onBackPressed() {
        getOut()
    }



}