package org.techtown.kanect

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import org.techtown.kanect.Adapter.ReviewAdapter
import org.techtown.kanect.Data.CafeIntel
import org.techtown.kanect.Data.UserIntel
import org.techtown.kanect.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDetailBinding

    private var cafeReviewRef = Firebase.database.reference.child("cafeReview")

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

        getCafeReviewData(cafeInfo.cafeName,userReviewRecyclerView)

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


    private fun getCafeReviewData(cafeName : String , userReviewRecyclerView : RecyclerView ): List<UserIntel> {

        val dataList: MutableList<UserIntel> = mutableListOf()

        cafeReviewRef = cafeReviewRef.child(cafeName)

        cafeReviewRef.addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                for (snapshot in snapshot.children) {

                    val userIntel = snapshot.getValue(UserIntel::class.java)

                    userIntel?.let {
                        dataList.add(it)
                    }

                }


                userReviewRecyclerView.adapter = ReviewAdapter(dataList)

            }

            override fun onCancelled( error: DatabaseError) {

            }

        })

        return dataList

    }



}