package org.techtown.kanect.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import org.techtown.kanect.Data.UserIntel


class CafeReviewViewModel : ViewModel() {

    private val _cafeReviewLiveData = MutableLiveData<List<UserIntel>>()
    val cafeReviewLiveData : LiveData<List<UserIntel>>
        get() = _cafeReviewLiveData

    fun getCafeReviewData(cafeName: String) {

        val cafeReviewRef = Firebase.database.reference.child("cafeReview").child(cafeName)

        cafeReviewRef.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                val dataList = mutableListOf<UserIntel>()

                for (snapshot in snapshot.children.reversed()) {

                    val userIntel = snapshot.getValue(UserIntel::class.java)

                    userIntel?.let {
                        dataList.add(it)
                    }

                }
                _cafeReviewLiveData.value = dataList
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

    }


}