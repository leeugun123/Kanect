package org.techtown.kanect.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class LoginViewModel : ViewModel() {

    private val database = FirebaseDatabase.getInstance()

    val loginStatus: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    suspend fun checkDataExistence(userId: String) {

        withContext(Dispatchers.IO) {

            val reference = database.reference.child("picAuths").child(userId)

            try {

                val dataSnapshot = reference.get().await()
                loginStatus.postValue(dataSnapshot.exists())

            } catch (e: Exception) {

                loginStatus.postValue(false)

            }

        }

    }


}