package org.techtown.kanect

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.techtown.kanect.Data.UserIntel
import org.techtown.kanect.Adapter.ReviewAdapter
import org.techtown.kanect.Data.CafeIntel
import org.techtown.kanect.ViewModel.CafeReviewViewModel
import org.techtown.kanect.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDetailBinding
    private lateinit var cafeReviewViewModel : CafeReviewViewModel
    private lateinit var userReviewRecyclerView : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        cafeReviewViewModel  = ViewModelProvider(this).get(CafeReviewViewModel::class.java)

        val cafeInfo = intent.getParcelableExtra<CafeIntel>("cafeIntel")

        Glide.with(this)
            .load(cafeInfo!!.cafeImg)
            .fitCenter()
            .into(binding.cafeImg)

        binding.cafeName.text = cafeInfo.cafeName
        binding.operTime.text = cafeInfo.operTime
        binding.entireSeat.text = cafeInfo.seat.toString() + "여석"
        binding.plugSeat.text = cafeInfo.plugSeat.toString() + "여석"

        userReviewRecyclerView = binding.userReviewRecyclerView
        userReviewRecyclerView.layoutManager = LinearLayoutManager(this)

        cafeReviewViewModel.getCafeReviewData(cafeInfo.cafeName)

        cafeReviewViewModel.cafeReviewLiveData.observe(this, Observer {

            userReviewRecyclerView.adapter = ReviewAdapter(it)

        })


        binding.reviewBut.setOnClickListener{

            val intent = Intent(this, ReviewRegisterActivity::class.java)
            intent.putExtra("cafeName",cafeInfo.cafeName)
            startActivity(intent)

        }

        binding.chatbut.setOnClickListener {

            val intent = Intent(this, ChatActivity::class.java)
            intent.putExtra("cafeName",cafeInfo.cafeName)
            startActivity(intent)

        }



    }


}