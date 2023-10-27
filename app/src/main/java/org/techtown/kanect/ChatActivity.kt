package org.techtown.kanect

import android.app.AlertDialog
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.MutableData
import com.google.firebase.database.Transaction
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.kakao.sdk.user.UserApiClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.techtown.kanect.Adapter.CafeListAdapter
import org.techtown.kanect.Adapter.ChatAdapter
import org.techtown.kanect.Data.ChatMessage
import org.techtown.kanect.databinding.ActivityChatBinding
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class ChatActivity : AppCompatActivity() {

    private lateinit var binding : ActivityChatBinding
    private lateinit var chatAdapter : ChatAdapter
    private val messages: MutableList<ChatMessage> = mutableListOf()
    // Firebase Realtime Database
    private val database = FirebaseDatabase.getInstance()

    private lateinit var cafeName : String

    private lateinit var chatRef : DatabaseReference
    private lateinit var chatNumRef : DatabaseReference

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Intent로 받아온 정보 업데이트

        cafeName = intent.getStringExtra("cafeName").toString()
        chatRef = database.reference.child("chat").child(cafeName)

        Glide.with(this)
            .load(intent.getStringExtra("cafeImg"))
            .override(100,100)
            .fitCenter()
            .into(binding.cafeImg)

        binding.cafeName.text = cafeName
        Toast.makeText(this, cafeName + "에 입장하였습니다.",Toast.LENGTH_SHORT).show()


        //카페 입장 수 조회
        chatNumRef = database.reference.child("chatNum").child(cafeName).child("entryCount")
        uploadCafeCount(true) // 입장

        // 채팅 참여자 수가 변경될때 마다 UI 업데이트
        val entryCountRef = database.reference.child("chatNum").child(cafeName).child("entryCount")

        entryCountRef.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val entryCount = dataSnapshot.getValue(Int::class.java) ?: 0

                binding.chatNum.text = entryCount.toString()

            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("Firebase", "데이터베이스 오류: ${databaseError.message}")
            }

        })

        //파이어베이스를 이용한 채팅 정보 조회


        chatAdapter = ChatAdapter(messages, UserKakaoInfo.userId)
        val layoutManager = LinearLayoutManager(this)
        binding.chatRecyclerView.layoutManager = layoutManager
        binding.chatRecyclerView.adapter = chatAdapter
        //Adapter 및 RecyclerView 연결

        // 이전 메시지의 날짜를 저장하는 변수
4
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

                chatAdapter.notifyDataSetChanged()
                binding.chatRecyclerView.scrollToPosition(messages.size - 1)//최근 메세지로 View 이동

            }

            override fun onCancelled(error: DatabaseError) {

            }


        })


        binding.sendButton.setOnClickListener {

            val messageText = binding.messageInputEditText.text.toString()

            if(messageText.isNotBlank()){

                val message = ChatMessage(
                    UserKakaoInfo.userId,
                    UserKakaoInfo.userImg,
                    UserKakaoInfo.userName,
                    messageText,
                    getCurrentTimeAsString(),
                    getCurrentDateAsInt(),
                    false
                )

                chatRef.push().setValue(message)
                binding.messageInputEditText.text.clear()


            }

        }//전송 버튼



    }

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

    private fun getCurrentDateAsInt(): String {
        // 현재 날짜를 가져옵니다.
        val currentDate = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalDate.now()
        } else {
            TODO("VERSION.SDK_INT < O")
        }

        // 날짜를 yyyyMMdd 형식의 숫자로 반환합니다.
        val formatter = DateTimeFormatter.ofPattern("yyyyMMdd")
        return currentDate.format(formatter)

    }




    private fun getOut(){

        val alertDialogBuilder = AlertDialog.Builder(this)


        alertDialogBuilder.setTitle("채팅방 퇴장")
        alertDialogBuilder.setMessage("$cafeName 채팅방을 퇴장하시겠습니까?")

        // "예" 버튼 설정 및 클릭 리스너 추가
        alertDialogBuilder.setPositiveButton("예") { dialog, which ->

            uploadCafeCount(false)
            finish()

        }

        alertDialogBuilder.setNegativeButton("아니요") { dialog, which ->

        }


        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()


    }

    override fun onBackPressed() {
        getOut()
    }



}