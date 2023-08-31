package org.techtown.kanect.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import org.techtown.kanect.Adapter.CafeTalkAdapter
import org.techtown.kanect.Data.CafeChatInfo
import org.techtown.kanect.R
import org.techtown.kanect.databinding.FragmentTalkBinding


class TalkFragment : Fragment() {

    private lateinit var binding : FragmentTalkBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentTalkBinding.inflate(inflater,container,false)
        
        val cafeChatListRecyclerView : RecyclerView = binding.cafeChatListRecycler
        cafeChatListRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        val cafeChatList = listOf(

            CafeChatInfo("https://firebasestorage.googleapis.com/v0/b/kanect-ced83.appspot.com/o/starbucks_logo.PNG?alt=media&token=d2a0d513-dacd-4862-8e7e-ec2decc5d91e",
                "스타벅스(공릉점)", 20),
            CafeChatInfo("https://firebasestorage.googleapis.com/v0/b/kanect-ced83.appspot.com/o/twosomeplace_logo.PNG?alt=media&token=89756cde-060f-47f5-a69b-227f80d534b7",
                "투썸플레이스(공릉점)", 10),
            CafeChatInfo("https://firebasestorage.googleapis.com/v0/b/kanect-ced83.appspot.com/o/tomtom_logo.PNG?alt=media&token=4b68af13-cd9c-4230-8a0d-5c8460437ee6",
                "탐탐(공릉점)", 16)


        )

        cafeChatListRecyclerView.adapter = CafeTalkAdapter(cafeChatList)


        return binding?.root

    }


}