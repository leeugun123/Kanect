package org.techtown.kanect

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.Glide.with
import com.google.firebase.storage.FirebaseStorage
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import org.techtown.kanect.databinding.ActivityAuthBinding
import java.io.ByteArrayOutputStream

class AuthActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAuthBinding
    private val REQUEST_IMAGE_CAPTURE = 1

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


            Toast.makeText(this,"인증이 완료되었습니다.",Toast.LENGTH_SHORT).show()

        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            val imageBitmap = data?.extras?.get("data") as Bitmap

            Glide.with(this)
                .load(imageBitmap)
                .centerCrop()
                .into(binding.authPic)
        }

    }



}