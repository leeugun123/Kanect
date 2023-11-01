package org.techtown.kanect.Object

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale

object GetTime {

    fun opExist(startTime : Int, endTime : Int): Boolean {

        val currentTime = Calendar.getInstance()
        val curTime = currentTime.get(Calendar.HOUR_OF_DAY) * 100 + currentTime.get(Calendar.MINUTE)

        return curTime in startTime..endTime

    }

   fun getCurrentDate() : String {

        val currentDate = Calendar.getInstance().time
        val dateFormat = SimpleDateFormat("yy/MM/dd", Locale.getDefault())
        return dateFormat.format(currentDate)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getCurrentTimeAsString(): String {

        val currentTime = LocalTime.now()
        val formatter = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            DateTimeFormatter.ofPattern("a hh:mm")
        } else {
            TODO("VERSION.SDK_INT < O")
        }
        return currentTime.format(formatter)


    }

    fun getCurrentDateAsInt(): String {
        // 현재 날짜를 가져옵니다.
        val currentDate = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalDate.now()
        } else {
            TODO("VERSION.SDK_INT < O")
        }

        // 날짜를 yyyyMMdd 형식의 숫자로 반환합니다.
        val formatter = DateTimeFormatter.ofPattern("yyyyMMdd")
        return currentDate.format(formatter)

    }

}