package org.techtown.kanect

import android.app.AlertDialog
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.MutableData
import com.google.firebase.database.Transaction
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.techtown.kanect.Adapter.CafeListAdapter
import org.techtown.kanect.Adapter.ChatAdapter
import org.techtown.kanect.Data.ChatMessage
import org.techtown.kanect.Object.CafeInit
import org.techtown.kanect.Object.GetCafeNum
import org.techtown.kanect.Object.GetTime
import org.techtown.kanect.Object.UserKakaoInfo
import org.techtown.kanect.ViewModel.CafeCountViewModel
import org.techtown.kanect.ViewModel.ChatDataViewModel
import org.techtown.kanect.databinding.ActivityChatBinding
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class ChatActivity : AppCompatActivity() {

    private lateinit var binding : ActivityChatBinding
    private lateinit var chatAdapter : ChatAdapter
    private val messages: MutableList<ChatMessage> = mutableListOf()
    // Firebase Realtime Database
    private lateinit var cafeName : String


    private lateinit var cafeCountViewModel : CafeCountViewModel
    private lateinit var chatDataViewModel : ChatDataViewModel


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChatBinding.inflate(layoutInflater)
        cafeCountViewModel = ViewModelProvider(this).get(CafeCountViewModel::class.java)
        chatDataViewModel = ViewModelProvider(this).get(ChatDataViewModel::class.java)
        setContentView(binding.root)

        chatInit()
        //UI 업데이트
        //uploadCafeCount(true)
        // 입장


        cafeCountViewModel.getCafeNum(cafeName)
        chatDataViewModel.getChatMessage(cafeName)

        cafeCountViewModel.cafeChatNum.observe(this) { cafeChatNum ->
            binding.chatNum.text = cafeChatNum.toString() + "명"
        }

        chatDataViewModel.chatMessageLiveData.observe(this){ chatList->
            chatUpdate(chatList)
        }


        binding.sendButton.setOnClickListener {

            /*
            val messageText = binding.messageInputEditText.text.toString()

            if(messageText.isNotBlank()){

                val message = ChatMessage(
                    UserKakaoInfo.userId,
                    UserKakaoInfo.userImg,
                    UserKakaoInfo.userName,
                    messageText,
                    GetTime.getCurrentTimeAsString(),
                    GetTime.getCurrentDateAsInt(),
                    false
                )

                chatRef.push().setValue(message)
                binding.messageInputEditText.text.clear()


            }

            */

        }//전송 버튼



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

    /*
    private fun uploadCafeCount(entry : Boolean){
        // 카페 입장 시 인원 추가
        chatNumRef.runTransaction(object : Transaction.Handler {

            override fun doTransaction(mutableData : MutableData): Transaction.Result {

                var currentCount = mutableData.getValue(Int::class.java) ?: 0

                if(entry)
                    currentCount++
                else{
                    if(currentCount != 0)
                        currentCount--;
                }

                mutableData.value = currentCount

                return Transaction.success(mutableData)

            }

            override fun onComplete(databaseError: DatabaseError?, committed: Boolean, dataSnapshot: DataSnapshot?) {

                if (databaseError == null) {

                    if (committed) {
                        Log.e("Firebase", "입장 인원 업데이트 성공")
                    }
                    else {
                        Log.e("Firebase", "입장 인원 업데이트 실패")
                    }

                } else {
                    Log.e("Firebase", "입장 인원 업데이트 오류: ${databaseError.message}")
                }

            }

        })



    }


*/

    private fun getOut(){

        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("채팅방 퇴장")
        alertDialogBuilder.setMessage("$cafeName 채팅방을 퇴장하시겠습니까?")

        // "예" 버튼 설정 및 클릭 리스너 추가
        alertDialogBuilder.setPositiveButton("예") { _, _ ->

            //uploadCafeCount(false)
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