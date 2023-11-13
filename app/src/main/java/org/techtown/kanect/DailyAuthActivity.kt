package org.techtown.kanect

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import com.kakao.sdk.user.UserApiClient
import org.techtown.kanect.Data.DailyAuth
import org.techtown.kanect.Data.PicAuth
import org.techtown.kanect.Object.GetTime
import org.techtown.kanect.ViewModel.TakePicViewModel
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
    private lateinit var takePicViewModel : TakePicViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDailyAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
        takePicViewModel = ViewModelProvider(this).get(TakePicViewModel::class.java)


        binding.takePicBut.setOnClickListener { usingCamera() }

        binding.authBut.setOnClickListener {

            if(dailyAuthPic && binding.authText.text.isNotBlank()){

                showLoadingState()
                takePicViewModel.uploadImageDaily(imageBitmap , binding.authText.text.toString())


            }else{
                Toast.makeText(this, "사진과 글을 올려주세요.", Toast.LENGTH_SHORT).show()
            }

        }

        takePicViewModel.uploadDaily.observe(this) { isUploaded ->

            if (isUploaded) {
                hideLoadingState()
                Toast.makeText(this, "데이터 업로드 성공", Toast.LENGTH_SHORT).show()
                finish()
            }

        }


    }

    private fun usingCamera() {

        TedPermission.create()
            .setPermissionListener(object : PermissionListener {
                override fun onPermissionGranted() {
                    val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE)
                }
                override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {}
            })
            .setDeniedMessage("카메라 권한이 필요합니다.\n[설정]에서 권한을 허용해주세요.")
            .setPermissions(android.Manifest.permission.CAMERA)
            .check()

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

    private fun showLoadingState() {
        // Show the ProgressBar
        binding.progressBar.visibility = View.VISIBLE

        // Disable user interaction while loading
        window.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
    }

    private fun hideLoadingState() {
        // Hide the ProgressBar
        binding.progressBar.visibility = View.GONE

        // Enable user interaction after loading
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }


}