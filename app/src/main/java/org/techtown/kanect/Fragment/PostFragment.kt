package org.techtown.kanect.Fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.techtown.kanect.Adapter.DailyAuthAdapter
import org.techtown.kanect.DailyAuthActivity
import org.techtown.kanect.Data.DailyAuth
import org.techtown.kanect.databinding.FragmentPostBinding


class PostFragment : Fragment() {

    private lateinit var binding : FragmentPostBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentPostBinding.inflate(inflater,container,false)

        binding.authBut.setOnClickListener {

            val intent = Intent(requireContext(), DailyAuthActivity::class.java)
            startActivity(intent)

        }

        val dailyAuthRecyclerView : RecyclerView = binding.authRecyclerView
        dailyAuthRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        var dailyAuthList : List<DailyAuth>? = null


        //가져오기
        if(dailyAuthList != null)
            dailyAuthRecyclerView.adapter = DailyAuthAdapter(dailyAuthList!!)





        return binding.root

    }


}