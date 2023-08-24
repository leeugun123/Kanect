package org.techtown.kanect

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.techtown.kanect.Adapter.ReviewAdapter
import org.techtown.kanect.Data.CafeIntel
import org.techtown.kanect.Data.UserIntel
import org.techtown.kanect.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val cafeInfo = intent.getParcelableExtra<CafeIntel>("cafeIntel")

        Glide.with(this)
            .load(cafeInfo!!.cafeImg)
            .fitCenter()
            .into(binding.cafeImg)

        binding.cafeName.text = cafeInfo.cafeName
        binding.operTime.text = cafeInfo.operTime
        binding.entireSeat.text = cafeInfo.seat.toString() + "여석"
        binding.plugSeat.text = cafeInfo.plugSeat.toString() + "여석"

        val userReviewRecyclerView : RecyclerView = binding.userReviewRecyclerView
        userReviewRecyclerView.layoutManager = LinearLayoutManager(this)

        val userReviewList = listOf(

            UserIntel("https://firebasestorage.googleapis.com/v0/b/kanect-ced83.appspot.com/o/twosomeplace_logo.PNG?alt=media&token=89756cde-060f-47f5-a69b-227f80d534b7",
                "이유건","공부하기 너무 좋은 환경이에요!!","23/03/20"),
            UserIntel("https://firebasestorage.googleapis.com/v0/b/kanect-ced83.appspot.com/o/twosomeplace_logo.PNG?alt=media&token=89756cde-060f-47f5-a69b-227f80d534b7",
                "정동하","너무 시끄럽지도 않는 분위기에 친구랑 같이 토론하면서 공부 할 수 있어서 좋았음.","23/03/20"),
            UserIntel("https://firebasestorage.googleapis.com/v0/b/kanect-ced83.appspot.com/o/twosomeplace_logo.PNG?alt=media&token=89756cde-060f-47f5-a69b-227f80d534b7",
                "아무개","ㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋ","23/03/21"),
            UserIntel("https://firebasestorage.googleapis.com/v0/b/kanect-ced83.appspot.com/o/twosomeplace_logo.PNG?alt=media&token=89756cde-060f-47f5-a69b-227f80d534b7",
                "아무개","ㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋ","23/03/21")


        )
        //파이어베이스를 통해 리뷰 정보 가져오기

        userReviewRecyclerView.adapter = ReviewAdapter(userReviewList)


        binding.reviewBut.setOnClickListener{

            val intent = Intent(this, ReviewRegisterActivity::class.java)
            intent.putExtra("cafeName",cafeInfo.cafeName)
            startActivity(intent)

        }

        binding.mycafeBut.setOnClickListener {


        }

        binding.chatbut.setOnClickListener {


        }
        //버튼




    }



}