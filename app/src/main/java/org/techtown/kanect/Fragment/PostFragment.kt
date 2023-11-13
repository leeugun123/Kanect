package org.techtown.kanect.Fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.techtown.kanect.Adapter.DailyAuthAdapter
import org.techtown.kanect.DailyAuthActivity
import org.techtown.kanect.ViewModel.DailyGetViewModel
import org.techtown.kanect.databinding.FragmentPostBinding


class PostFragment : Fragment() {

    private lateinit var binding : FragmentPostBinding
    private lateinit var dailyAuthRecyclerView : RecyclerView
    private lateinit var dailyGetViewModel : DailyGetViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentPostBinding.inflate(inflater,container,false)

        dailyAuthRecyclerView = binding.authRecyclerView
        dailyAuthRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        binding.authBut.setOnClickListener {

            val intent = Intent(requireContext(), DailyAuthActivity::class.java)
            startActivity(intent)

        }

        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dailyGetViewModel = ViewModelProvider(requireActivity()).get(DailyGetViewModel::class.java)

        dailyGetViewModel.dailyGetLiveData.observe(viewLifecycleOwner) { dailyList ->
            dailyAuthRecyclerView.adapter = DailyAuthAdapter(dailyList)
        }

        dailyGetViewModel.getDailyGetData()

    }


}