package org.techtown.kanect.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.techtown.kanect.Adapter.CafeListAdapter
import org.techtown.kanect.Data.CafeIntel
import org.techtown.kanect.Object.CafeInit
import org.techtown.kanect.Object.GetCafeNum
import org.techtown.kanect.Object.GetTime
import org.techtown.kanect.ViewModel.CafeCountViewModel
import org.techtown.kanect.databinding.FragmentListBinding
import java.util.Calendar


class ListFragment : Fragment() {

    private lateinit var binding : FragmentListBinding
    private lateinit var cafeCountViewModel : CafeCountViewModel
    private lateinit var cafeListRecyclerView : RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        cafeCountViewModel = ViewModelProvider(requireActivity()).get(CafeCountViewModel::class.java)
        cafeCountViewModel.getCafeData(CafeInit.cafeList)

    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentListBinding.inflate(inflater,container,false)

        cafeListRecyclerView = binding.cafeListRecycler
        cafeListRecyclerView.layoutManager = LinearLayoutManager(requireContext())


        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cafeCountViewModelObserve()
    }

    private fun cafeCountViewModelObserve(){

        cafeCountViewModel.cafeList.observe(viewLifecycleOwner) { cafeList ->
            cafeListRecyclerView.adapter = CafeListAdapter(cafeList)
        }

    }





}