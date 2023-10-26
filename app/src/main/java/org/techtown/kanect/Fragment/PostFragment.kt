package org.techtown.kanect.Fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import org.techtown.kanect.Adapter.DailyAuthAdapter
import org.techtown.kanect.DailyAuthActivity
import org.techtown.kanect.Data.DailyAuth
import org.techtown.kanect.databinding.FragmentPostBinding


class PostFragment : Fragment() {

    private lateinit var binding : FragmentPostBinding
    private lateinit var authRef : DatabaseReference
    private val database = FirebaseDatabase.getInstance()
    private var dailyAuthList : MutableList<DailyAuth> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)





    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentPostBinding.inflate(inflater,container,false)

        val dailyAuthRecyclerView : RecyclerView = binding.authRecyclerView
        dailyAuthRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        authRef = database.reference.child("DailyAuths")

        authRef.addListenerForSingleValueEvent(object : ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {

                dailyAuthList.clear()

                for(childSnapshot in snapshot.children){

                    val dailyAuth = childSnapshot.getValue(DailyAuth::class.java)

                    dailyAuth?.let {
                        dailyAuthList.add(it)
                    }

                }

                dailyAuthList.reverse()

                dailyAuthRecyclerView.adapter = DailyAuthAdapter(dailyAuthList)
                dailyAuthRecyclerView.scrollToPosition(0)

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })




        dailyAuthRecyclerView.adapter = DailyAuthAdapter(dailyAuthList!!)



        binding.authBut.setOnClickListener {

            val intent = Intent(requireContext(), DailyAuthActivity::class.java)
            startActivity(intent)

        }







        return binding.root

    }


}