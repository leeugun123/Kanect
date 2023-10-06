package org.techtown.kanect.Fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.techtown.kanect.Adapter.CafeListAdapter
import org.techtown.kanect.Data.CafeIntel
import org.techtown.kanect.GetCafeNum
import org.techtown.kanect.databinding.FragmentListBinding
import java.util.Calendar


class ListFragment : Fragment() {

    private lateinit var binding : FragmentListBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentListBinding.inflate(inflater,container,false)

        val cafeListRecyclerView : RecyclerView = binding.cafeListRecycler
        cafeListRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        val cafeList = listOf(

            CafeIntel("https://firebasestorage.googleapis.com/v0/b/kanect-ced83.appspot.com/o/starbucks_logo.PNG?alt=media&token=d2a0d513-dacd-4862-8e7e-ec2decc5d91e",
                "스타벅스(공릉점)",
                30,
                false, opExist(730,2200), cur_seat = 0, operTime = "07:30 ~ 22:00", plugSeat = 20
            ) ,

            CafeIntel("https://firebasestorage.googleapis.com/v0/b/kanect-ced83.appspot.com/o/twosomeplace_logo.PNG?alt=media&token=89756cde-060f-47f5-a69b-227f80d534b7",
                "투썸플레이스(공릉점)", 30,
                false, opExist(900,2400), cur_seat = 0, operTime = "09:00 ~ 24:00", plugSeat = 10
            ),

            CafeIntel("https://firebasestorage.googleapis.com/v0/b/kanect-ced83.appspot.com/o/tomtom_logo.PNG?alt=media&token=4b68af13-cd9c-4230-8a0d-5c8460437ee6",
                "탐탐(공릉점)", 30,
            true, opExist(0,2400), cur_seat = 0, operTime = "00:00 ~ 24:00", plugSeat = 30
            )
            //전체 좌석 , 플러그 좌석 수 조사

         )



        val coroutineScope = CoroutineScope(Dispatchers.Main)

        // 카페 정보를 가져오고 해당 정보를 사용하여 어댑터를 설정
        coroutineScope.launch {

            for (cafe in cafeList) {

                val cafeName = cafe.cafeName
                val entryCount = GetCafeNum.getCafeNum(cafeName)

                cafe.cur_seat = entryCount // cur_seat 업데이트

            }

            cafeListRecyclerView.adapter = CafeListAdapter(cafeList)
        }



        return binding.root

    }

    private fun opExist(startTime : Int, endTime : Int): Boolean {

        val currentTime = Calendar.getInstance()
        val curTime = currentTime.get(Calendar.HOUR_OF_DAY) * 100 + currentTime.get(Calendar.MINUTE)

        return curTime in startTime..endTime

    }

    //비동기












}