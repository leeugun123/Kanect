package org.techtown.kanect.UI.Activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.AuthErrorCause
import com.kakao.sdk.user.UserApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.techtown.kanect.Object.UserKakaoInfo
import org.techtown.kanect.ViewModel.KakaoViewModel
import org.techtown.kanect.ViewModel.LoginViewModel
import org.techtown.kanect.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding
    private lateinit var loginViewModel : LoginViewModel
    private lateinit var kakaoViewModel : KakaoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        kakaoViewModel = ViewModelProvider(this).get(KakaoViewModel::class.java)

        //----------------------카카오 로그인 api 관련 코드---------------------------------

        UserApiClient.instance.accessTokenInfo { tokenInfo, error ->

            if (error != null) {
                Toast.makeText(this, "카카오 로그인 실패", Toast.LENGTH_SHORT).show()
            }
            else if (tokenInfo != null) {

                Toast.makeText(this, "카카오 로그인", Toast.LENGTH_SHORT).show()
                moveNextActivity()

            }


        }

        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                when {
                    error.toString() == AuthErrorCause.AccessDenied.toString() -> {
                        Toast.makeText(this, "접근이 거부 됨(동의 취소)", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.InvalidClient.toString() -> {
                        Toast.makeText(this, "유효하지 않은 앱", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.InvalidGrant.toString() -> {
                        Toast.makeText(this, "인증 수단이 유효하지 않아 인증할 수 없는 상태", Toast.LENGTH_SHORT)
                            .show()
                    }
                    error.toString() == AuthErrorCause.InvalidRequest.toString() -> {
                        Toast.makeText(this, "요청 파라미터 오류", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.InvalidScope.toString() -> {
                        Toast.makeText(this, "유효하지 않은 scope ID", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.Misconfigured.toString() -> {
                        Toast.makeText(this, "설정이 올바르지 않음(android key hash)", Toast.LENGTH_SHORT)
                            .show()
                    }
                    error.toString() == AuthErrorCause.ServerError.toString() -> {
                        Toast.makeText(this, "서버 내부 에러", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.Unauthorized.toString() -> {
                        Toast.makeText(this, "앱이 요청 권한이 없음", Toast.LENGTH_SHORT).show()
                    }
                    else -> { // Unknown
                        Toast.makeText(this, "기타 에러", Toast.LENGTH_SHORT).show()
                    }


                }
            } else if (token != null) {

                Toast.makeText(this, "카카오 로그인", Toast.LENGTH_SHORT).show()
                moveNextActivity()

            }


        }

        getKakaoInfo()
        //카카오 정보 가져오기

        binding.kakaoLoginBut.setOnClickListener {

            if(UserApiClient.instance.isKakaoTalkLoginAvailable(this)){
                UserApiClient.instance.loginWithKakaoTalk(this,callback = callback)
            }
            else{
                UserApiClient.instance.loginWithKakaoAccount(this,callback = callback)
            }

        }




    }

    private fun moveNextActivity(){

        var intent : Intent?

        GlobalScope.launch(Dispatchers.Main) {
            loginViewModel.checkDataExistence(UserKakaoInfo.userId.toString())
        }

        loginViewModel.loginStatus.observe(this) { exists ->

            intent = if (exists) {
                Intent(this, MainActivity::class.java)
            } else {
                Intent(this, AuthActivity::class.java)
            }

            startActivity(intent)
            finish()

        }


    }

    //코루틴을 사용하여 백그라운드 스레드에서 userId가 파이어베이스에 존재하는지 체크

    private fun getKakaoInfo(){

        kakaoViewModel.fetchUserInfo()

        kakaoViewModel.userId.observe(this) { userId ->
            UserKakaoInfo.userId = userId
        }

        kakaoViewModel.userName.observe(this) { userName ->
            UserKakaoInfo.userName = userName
        }

        kakaoViewModel.userImg.observe(this) { userImg ->
            UserKakaoInfo.userImg = userImg.toString()
        }

    }



}