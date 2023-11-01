package org.techtown.kanect

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.techtown.kanect.Data.UserIntel
import org.techtown.kanect.Object.UserKakaoInfo
import org.techtown.kanect.databinding.ActivityReviewRegisterBinding

class ReviewRegisterActivity : AppCompatActivity() {

    private lateinit var binding : ActivityReviewRegisterBinding
    private lateinit var cafeName : String
    private lateinit var database : FirebaseDatabase
    private lateinit var cafeReviewRef : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReviewRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        reviewRegisterInit()


        binding.registerBut.setOnClickListener {

            if(binding.reviewEdit.text.toString().isNotEmpty()){

                val newUserRef : DatabaseReference = cafeReviewRef.child(cafeName).push()

                newUserRef.setValue(UserIntel(UserKakaoInfo.userImg , UserKakaoInfo.userName , binding.reviewEdit.text.toString(), org.techtown.kanect.Object.GetTime.getCurrentDate()))
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

    private fun reviewRegisterInit(){

        database = FirebaseDatabase.getInstance()
        cafeReviewRef = database.getReference("cafeReview")


        cafeName = intent.getStringExtra("cafeName").toString()

        Glide.with(this)
            .load(UserKakaoInfo.userImg)
            .circleCrop()
            .override(100,100)
            .into(binding.userImg)

        binding.userName.text = UserKakaoInfo.userName



    }





}