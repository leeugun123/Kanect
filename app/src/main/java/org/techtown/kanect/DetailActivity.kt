package org.techtown.kanect

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import org.techtown.kanect.Data.CafeIntel
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

        binding.reviewBut.setOnClickListener{


        }

        binding.mycafeBut.setOnClickListener {


        }

        binding.chatbut.setOnClickListener {


        }
        //버튼




    }



}