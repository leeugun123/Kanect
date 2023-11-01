package org.techtown.kanect

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.techtown.kanect.Data.UserIntel
import org.techtown.kanect.Object.GetTime
import org.techtown.kanect.Object.UserKakaoInfo
import org.techtown.kanect.ViewModel.ReviewRegisterViewModel
import org.techtown.kanect.databinding.ActivityReviewRegisterBinding

class ReviewRegisterActivity : AppCompatActivity() {

    private lateinit var binding : ActivityReviewRegisterBinding
    private lateinit var reviewRegisterViewModel : ReviewRegisterViewModel
    private lateinit var cafeName : String
    private lateinit var database : FirebaseDatabase
    private lateinit var cafeReviewRef : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReviewRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)


        reviewRegisterViewModel = ViewModelProvider(this).get(ReviewRegisterViewModel::class.java)

        reviewRegisterInit()


        binding.registerBut.setOnClickListener {

            val reviewText = binding.reviewEdit.text.toString()

            if (reviewText.isNotEmpty()) {

                reviewRegisterViewModel.registerReview(
                    cafeReviewRef,
                    cafeName,
                    UserKakaoInfo.userImg,
                    UserKakaoInfo.userName,
                    reviewText,
                    GetTime.getCurrentDate()
                )

            } else {

                Toast.makeText(this, "적어도 한 글자 이상 작성해주세요.", Toast.LENGTH_SHORT).show()

            }

        }//후기 쓰기


        reviewRegisterViewModel.registerStatus.observe(this) { isRegistered ->

            if (isRegistered) {
                Toast.makeText(this, "후기 작성 완료", Toast.LENGTH_SHORT).show()
                finish()

            } else {

                Toast.makeText(this, "후기 작성 실패. 네트워크 오류", Toast.LENGTH_SHORT).show()

            }

        }


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