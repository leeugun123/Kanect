package org.techtown.kanect.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import org.techtown.kanect.Adapter.DailyAuthAdapter
import org.techtown.kanect.Data.DailyAuth

class DailyGetViewModel : ViewModel(){

    private val _dailyGetLiveData = MutableLiveData<List<DailyAuth>>()
    val dailyGetLiveData : LiveData<List<DailyAuth>>
        get() = _dailyGetLiveData

    fun getDailyGetData(){

        val authRef = Firebase.database.reference.child("DailyAuths")

        authRef.addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                val dailyAuthList = mutableListOf<DailyAuth>()

                for(childSnapshot in snapshot.children.reversed()){

                    val dailyAuth = childSnapshot.getValue(DailyAuth::class.java)

                    dailyAuth?.let {
                        dailyAuthList.add(it)
                    }

                }

                _dailyGetLiveData.value = dailyAuthList

            }

            override fun onCancelled(error: DatabaseError) {

            }


        })


    }




}