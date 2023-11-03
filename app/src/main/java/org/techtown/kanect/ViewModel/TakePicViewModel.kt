package org.techtown.kanect.ViewModel

import android.graphics.Bitmap
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.kakao.sdk.user.UserApiClient
import org.techtown.kanect.Data.DailyAuth
import org.techtown.kanect.Data.PicAuth
import org.techtown.kanect.Object.GetTime
import org.techtown.kanect.Object.UserKakaoInfo
import java.io.ByteArrayOutputStream

class TakePicViewModel : ViewModel() {

    private val _uploadAuth = MutableLiveData<Boolean>()
    val uploadAuth: LiveData<Boolean>
        get() = _uploadAuth



    private val _uploadDaily = MutableLiveData<Boolean>()

    val uploadDaily : LiveData<Boolean>
        get() = _uploadDaily

    fun uploadImageAuth(imageBitmap: Bitmap, userId: String) {

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
                        _uploadAuth.value = true
                    }.addOnFailureListener {
                        _uploadAuth.value = false
                    }

                }

            }

        }

    }


    fun uploadImageDaily(imageBitmap : Bitmap , authText : String) {

        // 이미지 파일 이름을 현재 시간으로 지정
        val imageFileName = "imageDailyAuth_${System.currentTimeMillis()}.jpg"

        val storage = FirebaseStorage.getInstance()
        val storageRef: StorageReference = storage.reference
        val imageRef: StorageReference = storageRef.child("imagesDailyAuth/$imageFileName")

        // Bitmap을 ByteArray로 변환하여 업로드
        val baos = ByteArrayOutputStream()
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)

        val imageData: ByteArray = baos.toByteArray()
        val uploadTask = imageRef.putBytes(imageData)

        uploadTask.addOnCompleteListener { task ->

                if (task.isSuccessful) {
                    // 업로드 성공
                    imageRef.downloadUrl.addOnSuccessListener { uri ->

                        val dailyAuthImageUrl = uri.toString()

                            val dailyAuth = DailyAuth(UserKakaoInfo.userName,UserKakaoInfo.userImg,dailyAuthImageUrl,authText, GetTime.getCurrentDate())
                            val databaseRef = FirebaseDatabase.getInstance().reference
                            val newDailyAuthRef = databaseRef.child("DailyAuths").push()

                            newDailyAuthRef.setValue(dailyAuth).addOnSuccessListener {
                                    _uploadDaily.value = true
                            }.addOnFailureListener {
                                    _uploadDaily.value = false
                            }

                    }

                }
                else {
                    // 업로드 실패
                }

        }


    }


}