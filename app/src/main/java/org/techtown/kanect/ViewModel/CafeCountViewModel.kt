package org.techtown.kanect.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.techtown.kanect.Data.CafeChatInfo
import org.techtown.kanect.Data.CafeIntel
import org.techtown.kanect.Object.GetCafeNum

class CafeCountViewModel : ViewModel() {

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    private val _cafeList = MutableLiveData<List<CafeIntel>>()
    val cafeList: LiveData<List<CafeIntel>> get() = _cafeList

    private val _cafeChatList = MutableLiveData<List<CafeChatInfo>>()
    val cafeChatList: LiveData<List<CafeChatInfo>> get() = _cafeChatList

    fun getCafeListData(cafeList: List<CafeIntel>) {

        coroutineScope.launch {

            val updatedList = cafeList.map { cafe ->

                val entryCount = GetCafeNum.getCafeNum(cafe.cafeName)
                cafe.copy(cur_seat = entryCount)


            }

            _cafeList.value = updatedList

        }

    }

    fun getCafeChatData(cafeChatList: List<CafeChatInfo>) {

        coroutineScope.launch {

            val updatedList = cafeChatList.map { cafe ->

                val entryCount = GetCafeNum.getCafeNum(cafe.cafeName)
                cafe.copy(seat = entryCount)

            }

            _cafeChatList.value = updatedList

        }

    }



}