package org.techtown.kanect.ViewModel

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import org.techtown.kanect.Data.PicAuth
import java.io.ByteArrayOutputStream

class TakePicViewModel : ViewModel() {

    private val _uploadSuccess = MutableLiveData<Boolean>()
    val uploadSuccess: LiveData<Boolean>
        get() = _uploadSuccess

    fun uploadImageToFirebaseStorage(imageBitmap: Bitmap, userId: String) {

        val imageFileName = "image_${System.currentTimeMillis()}.jpg"
        val storage = FirebaseStorage.getInstance()
        val storageRef: StorageReference = storage.reference
        val imageRef: StorageReference = storageRef.child("images/$imageFileName")

        val baos = ByteArrayOutputStream()
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val imageData: ByteArray = baos.toByteArray()

        val uploadTask = imageRef.putBytes(imageData)

        uploadTask.addOnCompleteListener { task ->

            if (task.isSuccessful) {

                imageRef.downloadUrl.addOnSuccessListener { uri ->

                    val imageUrl = uri.toString()
                    val picAuth = PicAuth(userId, imageUrl)
                    val databaseRef = FirebaseDatabase.getInstance().reference
                    val newPicAuthRef = databaseRef.child("picAuths").child(userId)

                    newPicAuthRef.setValue(picAuth).addOnSuccessListener {
                        _uploadSuccess.value = true
                    }.addOnFailureListener {
                        _uploadSuccess.value = false
                    }

                }

            }

        }

    }





}