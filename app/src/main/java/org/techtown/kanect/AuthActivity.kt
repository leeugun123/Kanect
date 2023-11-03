package org.techtown.kanect


import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import org.techtown.kanect.Data.PicAuth
import org.techtown.kanect.Object.UserKakaoInfo
import org.techtown.kanect.ViewModel.TakePicViewModel
import org.techtown.kanect.databinding.ActivityAuthBinding
import java.io.ByteArrayOutputStream

class AuthActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAuthBinding
    private val REQUEST_IMAGE_CAPTURE = 1
    private var picComplete = false
    private lateinit var takePicViewModel : TakePicViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        takePicViewModel = ViewModelProvider(this).get(TakePicViewModel::class.java)


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

        takePicViewModel.uploadSuccess.observe(this) { isUploaded ->

            if (isUploaded) {
                Toast.makeText(this, "데이터 업로드 성공", Toast.LENGTH_SHORT).show()
            } else {
                // 데이터 업로드 실패 처리
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
            takePicViewModel.uploadImageToFirebaseStorage(imageBitmap , UserKakaoInfo.userId.toString())

        }

    }





    private fun moveNextActivity(){

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)

    }



}