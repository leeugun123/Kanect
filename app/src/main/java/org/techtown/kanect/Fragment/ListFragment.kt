package org.techtown.kanect.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.techtown.kanect.Adapter.CafeListAdapter
import org.techtown.kanect.Data.CafeIntel
import org.techtown.kanect.R
import org.techtown.kanect.databinding.FragmentListBinding


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
            CafeIntel("cafe1_img", "스타벅스(공릉점)", "전체 좌석 50", true, true, 23, true)
        )

        cafeListRecyclerView.adapter = CafeListAdapter(cafeList)

        return binding?.root

    }




}