package org.techtown.kanect.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.techtown.kanect.Adapter.CafeTalkAdapter
import org.techtown.kanect.Object.CafeInit
import org.techtown.kanect.ViewModel.CafeCountViewModel
import org.techtown.kanect.databinding.FragmentTalkBinding


class TalkFragment : Fragment() {

    private lateinit var binding : FragmentTalkBinding
    private lateinit var cafeCountViewModel : CafeCountViewModel
    private lateinit var cafeChatListRecyclerView : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentTalkBinding.inflate(inflater,container,false)

        cafeChatListRecyclerView = binding.cafeChatListRecycler
        cafeChatListRecyclerView.layoutManager = LinearLayoutManager(requireContext())


        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cafeCountViewModel = ViewModelProvider(requireActivity()).get(CafeCountViewModel::class.java)

        cafeCountViewModel.cafeChatList.observe(viewLifecycleOwner) { cafechatList ->
            cafeChatListRecyclerView.adapter = CafeTalkAdapter(cafechatList)
        }

        cafeCountViewModel.getCafeChatData(CafeInit.cafeChatList)

    }

    private fun cafeCountViewModelObserve(){



    }


}