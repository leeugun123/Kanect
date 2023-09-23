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
import org.techtown.kanect.Data.PicAuth
import org.techtown.kanect.databinding.ActivityAuthBinding
import java.io.ByteArrayOutputStream

class AuthActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAuthBinding
    private val REQUEST_IMAGE_CAPTURE = 1
    private var picComplete = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backBtn.setOnClickListener {
            finish()
        }//뒤로가기

        binding.takePicBut.setOnClickListener {

            // TedPermission을 사용하여 카메라 권한 요청
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

            if(picComplete){
                Toast.makeText(this,"인증이 완료되었습니다.",Toast.LENGTH_SHORT).show()
                moveNextActivity()

            }else{
                Toast.makeText(this,"인증사진을 찍어주세요.",Toast.LENGTH_SHORT).show()
            }



        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            picComplete = true;

            val imageBitmap = data?.extras?.get("data") as Bitmap

            Glide.with(this)
                .load(imageBitmap)
                .centerCrop()
                .into(binding.authPic)

            // Firebase Storage에 업로드
            uploadImageToFirebaseStorage(imageBitmap)


        }

    }

    private fun uploadImageToFirebaseStorage(imageBitmap : Bitmap) {

        // 이미지 파일 이름을 현재 시간으로 지정
        val imageFileName = "image_${System.currentTimeMillis()}.jpg"

        val storage = FirebaseStorage.getInstance()
        val storageRef: StorageReference = storage.reference
        val imageRef: StorageReference = storageRef.child("images/$imageFileName")

        // Bitmap을 ByteArray로 변환하여 업로드
        val baos = ByteArrayOutputStream()
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)

        val imageData: ByteArray = baos.toByteArray()

        val uploadTask = imageRef.putBytes(imageData)

        uploadTask.addOnCompleteListener { task ->

            if (task.isSuccessful) {
                // 업로드 성공
                imageRef.downloadUrl.addOnSuccessListener { uri ->

                    val imageUrl = uri.toString()

                    UserApiClient.instance.me { user, error ->

                        user?.id?.let { userId ->

                            val picAuth = PicAuth(userId.toString(), imageUrl)
                            val databaseRef = FirebaseDatabase.getInstance().reference
                            val newPicAuthRef = databaseRef.child("picAuths").child(userId.toString())

                            newPicAuthRef.setValue(picAuth)
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

    private fun moveNextActivity(){

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)

    }



}