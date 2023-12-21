package org.techtown.kanect.UI.Activity

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
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
        cafeDetailInit(cafeInfo!!)
        //카페 UI 업데이트


        binding.reviewBut.setOnClickListener{

            val intent = Intent(this, ReviewRegisterActivity::class.java)
            intent.putExtra("cafeName",cafeInfo.cafeName)
            startActivity(intent)

        }//리뷰 버튼

        binding.chatbut.setOnClickListener {

            val alertDialogBuilder = AlertDialog.Builder(this)

            alertDialogBuilder.setTitle("채팅방 입장")
            alertDialogBuilder.setMessage(cafeInfo.cafeName + " 채팅방에 입장하시겠습니까?")

            // "예" 버튼 설정 및 클릭 리스너 추가
            alertDialogBuilder.setPositiveButton("예") { dialog, which ->

                val intent = Intent(this, ChatActivity::class.java)
                intent.putExtra("cafeName",cafeInfo.cafeName)
                intent.putExtra("cafeImg",cafeInfo.cafeImg)
                startActivity(intent)

            }

            // "아니요" 버튼 설정 및 클릭 리스너 추가
            alertDialogBuilder.setNegativeButton("아니요") { dialog, which ->

            }

            // AlertDialog 생성 및 표시
            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()

        }//채팅 버튼


    }



    private fun cafeDetailInit(cafeInfo : CafeIntel){

        Glide.with(this)
            .load(cafeInfo!!.cafeImg)
            .fitCenter()
            .into(binding.cafeImg)

        binding.cafeName.text = cafeInfo.cafeName
        binding.operTime.text = cafeInfo.operTime
        binding.entireSeat.text = cafeInfo.seat.toString() + "여석"
        binding.plugSeat.text = cafeInfo.plugSeat.toString() + "여석"
        binding.curSeat.text = cafeInfo.cur_seat.toString() + "명"

        userReviewRecyclerView = binding.userReviewRecyclerView
        userReviewRecyclerView.layoutManager = LinearLayoutManager(this)


        cafeReviewViewModel.getCafeReviewData(cafeInfo.cafeName)

        cafeReviewViewModel.cafeReviewLiveData.observe(this, Observer {

            userReviewRecyclerView.adapter = ReviewAdapter(it)

        })


    }


}