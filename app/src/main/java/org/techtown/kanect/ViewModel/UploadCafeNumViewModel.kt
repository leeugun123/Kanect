package org.techtown.kanect.ViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.MutableData
import com.google.firebase.database.Transaction
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class UploadCafeNumViewModel : ViewModel() {

    private val database = Firebase.database
    private lateinit var chatNumRef : DatabaseReference

    fun uploadCafeCount(entry: Boolean, cafeName: String) {

        chatNumRef = database.reference.child("chatNum").child(cafeName).child("entryCount")

        chatNumRef.runTransaction(object : Transaction.Handler {

            override fun doTransaction(mutableData: MutableData): Transaction.Result {

                var currentCount = mutableData.getValue(Int::class.java) ?: 0
                currentCount = if (entry) currentCount + 1 else maxOf(0, currentCount - 1)
                mutableData.value = currentCount
                return Transaction.success(mutableData)

            }

            override fun onComplete(databaseError: DatabaseError?, committed: Boolean, dataSnapshot: DataSnapshot?) {

                if (databaseError == null) {
                    if (committed) {
                        Log.e("Firebase", "입장 인원 업데이트 성공")
                    } else {
                        Log.e("Firebase", "입장 인원 업데이트 실패")
                    }
                } else {
                    Log.e("Firebase", "입장 인원 업데이트 오류: ${databaseError.message}")
                }
            }

        })

    }

}
