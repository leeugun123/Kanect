package org.techtown.kanect

import android.app.Application
import com.kakao.sdk.common.KakaoSdk

class GlobalApplication : Application(){

    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(this, "b3dd32c725a016184cdfa6f176033403")
    }
}