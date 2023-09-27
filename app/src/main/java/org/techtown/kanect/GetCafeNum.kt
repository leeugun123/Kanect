package org.techtown.kanect

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.CompletableDeferred

object GetCafeNum {

    suspend fun getCafeNum(cafeName: String): Int {

        val database = FirebaseDatabase.getInstance()
        val entryCountRef = database.reference.child("chatNum").child(cafeName).child("entryCount")

        val deferred = CompletableDeferred<Int>()

        entryCountRef.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val entryCount = dataSnapshot.getValue(Int::class.java) ?: 0
                deferred.complete(entryCount)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                deferred.completeExceptionally(databaseError.toException())
            }
        })

        return try {
            deferred.await() // 데이터 가져오기를 기다림
        } catch (e: Exception) {
            // 예외 처리
            Log.e("Firebase", "데이터 가져오기 오류: ${e.message}")
            0 // 기본값 또는 오류 처리에 따라 반환값 설정
        }

    }



}