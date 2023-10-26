package org.techtown.kanect

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import com.kakao.sdk.user.UserApiClient
import org.techtown.kanect.Data.DailyAuth
import org.techtown.kanect.Data.PicAuth
import org.techtown.kanect.databinding.ActivityDailyAuthBinding
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class DailyAuthActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDailyAuthBinding
    private val REQUEST_IMAGE_CAPTURE = 2
    private var dailyAuthPic = false
    private lateinit var imageBitmap : Bitmap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDailyAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.takePicBut.setOnClickListener {

            TedPermission.create()
                .setPermissionListener(object : PermissionListener {

                    override fun onPermissionGranted() {

                        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                        startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE)

                    }

                    override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {

                    }

                })
                .setDeniedMessage("카메라 권한이 필요합니다.\n[설정]에서 권한을 허용해주세요.")
                .setPermissions(android.Manifest.permission.CAMERA)
                .check()


        }

        binding.authBut.setOnClickListener {

            if(dailyAuthPic && binding.authText.text.isNotBlank()){

                uploadImageToFirebaseStorage(imageBitmap , binding.authText.text.toString())

            }

        }



    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            dailyAuthPic = true;

            imageBitmap = data?.extras?.get("data") as Bitmap

            Glide.with(this)
                .load(imageBitmap)
                .centerCrop()
                .into(binding.authImageView)

        }



    }


    private fun uploadImageToFirebaseStorage(imageBitmap : Bitmap , authText : String) {

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

                    UserApiClient.instance.me { user, error ->

                        user?.let {

                            val userName = it!!.kakaoAccount!!.profile!!.nickname.toString()
                            val userImg = it.kakaoAccount!!.profile!!.profileImageUrl.toString()

                            val dailyAuth = DailyAuth(userName,userImg,dailyAuthImageUrl,authText, getCurrentDate())

                            val databaseRef = FirebaseDatabase.getInstance().reference
                            val newDailyAuthRef = databaseRef.child("DailyAuths").push()

                            newDailyAuthRef.setValue(dailyAuth)
                                .addOnSuccessListener {

                                    Toast.makeText(this, "데이터 업로드 성공", Toast.LENGTH_SHORT).show()

                                }

                                .addOnFailureListener {
                                    // 업로드 실패 처리
                                }

                        }

                    }//파이베이스에 데이터 올리기





                }

            }
            else {
                // 업로드 실패
            }

        }

    }

    private fun getCurrentDate(): String {

        val currentDate = Calendar.getInstance().time
        val dateFormat = SimpleDateFormat("yy/MM/dd", Locale.getDefault())
        return dateFormat.format(currentDate)

    }




}