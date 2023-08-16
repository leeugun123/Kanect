package org.techtown.kanect.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.techtown.kanect.R
import org.techtown.kanect.databinding.FragmentMyBinding


class MyFragment : Fragment() {

    private lateinit var binding : FragmentMyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentMyBinding.inflate(inflater,container,false)
        return binding?.root

    }


}