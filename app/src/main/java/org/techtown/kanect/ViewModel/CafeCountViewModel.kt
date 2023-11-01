package org.techtown.kanect.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.techtown.kanect.Data.CafeIntel
import org.techtown.kanect.Object.GetCafeNum

class CafeCountViewModel : ViewModel() {

    private val _cafeList = MutableLiveData<List<CafeIntel>>()
    val cafeList = _cafeList

    fun getCafeData(cafeList : List<CafeIntel>) {

        val coroutineScope = CoroutineScope(Dispatchers.Main)

        coroutineScope.launch {

            val list = mutableListOf<CafeIntel>()

            for (cafe in cafeList) {

                val cafeName = cafe.cafeName
                val entryCount = GetCafeNum.getCafeNum(cafeName)

                list.add(cafe.copy(cur_seat = entryCount))

            }
            _cafeList.value = list

        }

    }

}