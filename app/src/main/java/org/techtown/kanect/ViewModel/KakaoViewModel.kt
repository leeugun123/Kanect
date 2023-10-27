package org.techtown.kanect.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kakao.sdk.user.UserApiClient

class KakaoViewModel : ViewModel() {

    private val _userId = MutableLiveData<Long>()
    val userId : LiveData<Long> get() = _userId


    private val _userName = MutableLiveData<String>()
    val userName: LiveData<String> get() = _userName

    private val _userImg = MutableLiveData<String?>()
    val userImg: LiveData<String?> get() = _userImg

    init {
        _userName.value = ""
        _userImg.value = ""
    }

    fun fetchUserInfo() {

        UserApiClient.instance.me { user, error ->

            user?.let {

                _userId.value = it.id
                _userName.value = it.kakaoAccount?.profile?.nickname
                _userImg.value = it.kakaoAccount?.profile?.profileImageUrl

            }

        }

    }




}