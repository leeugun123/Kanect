package org.techtown.kanect

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.kakao.sdk.user.UserApiClient
import org.techtown.kanect.Data.UserIntel
import org.techtown.kanect.databinding.ActivityReviewRegisterBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class ReviewRegisterActivity : AppCompatActivity() {

    private lateinit var binding : ActivityReviewRegisterBinding

    private val database : FirebaseDatabase = FirebaseDatabase.getInstance()
    private val cafeReviewRef : DatabaseReference = database.getReference("cafeReview")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReviewRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val cafeInfo = intent.getStringExtra("cafeName")

        Glide.with(this)
            .load(UserKakaoInfo.userImg)
            .circleCrop()
            .override(100,100)
            .into(binding.userImg)

        binding.userName.text = UserKakaoInfo.userName


        binding.registerBut.setOnClickListener {

            if(binding.reviewEdit.text.toString().isNotEmpty()){

                val newUserRef : DatabaseReference = cafeReviewRef.child(cafeInfo.toString()).push()

                newUserRef.setValue(UserIntel(UserKakaoInfo.userImg , UserKakaoInfo.userName , binding.reviewEdit.text.toString(),getCurrentDate()))
                    .addOnSuccessListener {

                        Toast.makeText(this,"후기 작성 완료",Toast.LENGTH_SHORT).show()
                        finish()

                    }
                    .addOnFailureListener {
                        Toast.makeText(this,"후기 작성 실패. 네트워크 오류 ",Toast.LENGTH_SHORT).show()
                    }

            }else{
                Toast.makeText(this,"적어도 한 글자 이상 작성해주세요.",Toast.LENGTH_SHORT).show()
            }

        }//후기 쓰기


    }

    private fun getCurrentDate(): String {

        val currentDate = Calendar.getInstance().time
        val dateFormat = SimpleDateFormat("yy/MM/dd", Locale.getDefault())
        return dateFormat.format(currentDate)

    }



}