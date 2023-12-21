package org.techtown.kanect.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DatabaseReference
import org.techtown.kanect.Data.UserIntel

class ReviewRegisterViewModel : ViewModel() {

    private val _registerStatus = MutableLiveData<Boolean>()
    val registerStatus: LiveData<Boolean>
        get() = _registerStatus

    fun registerReview(cafeReviewRef : DatabaseReference, cafeName: String, userImg: String, userName: String, reviewText: String, currentDate: String) {

        val newUserRef: DatabaseReference = cafeReviewRef.child(cafeName).push()

        newUserRef.setValue(UserIntel(userImg, userName, reviewText, currentDate))
            .addOnSuccessListener {
                _registerStatus.value = true
            }
            .addOnFailureListener {
                _registerStatus.value = false
            }

    }


}