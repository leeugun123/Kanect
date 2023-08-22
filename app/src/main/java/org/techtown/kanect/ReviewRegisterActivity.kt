package org.techtown.kanect

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.kakao.sdk.user.UserApiClient
import org.techtown.kanect.databinding.ActivityReviewRegisterBinding

class ReviewRegisterActivity : AppCompatActivity() {

    private lateinit var binding : ActivityReviewRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReviewRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        UserApiClient.instance.me { user, error ->

            user?.let {

                Glide.with(this)
                    .load(it.kakaoAccount!!.profile!!.profileImageUrl)
                    .circleCrop()
                    .override(100,100)
                    .into(binding.userImg)

                binding.userName.text = it!!.kakaoAccount!!.profile!!.nickname


            }




        }






    }


}