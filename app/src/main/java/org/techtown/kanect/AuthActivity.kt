package org.techtown.kanect

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import org.techtown.kanect.databinding.ActivityAuthBinding

class AuthActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backBtn.setOnClickListener {
            finish()
        }//뒤로가기

        binding.takePicBut.setOnClickListener {


        }

        binding.authBut.setOnClickListener {


        }


    }



}