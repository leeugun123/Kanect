package org.techtown.kanect.Object

import java.util.Calendar

object GetTime {

    fun opExist(startTime : Int, endTime : Int): Boolean {

        val currentTime = Calendar.getInstance()
        val curTime = currentTime.get(Calendar.HOUR_OF_DAY) * 100 + currentTime.get(Calendar.MINUTE)

        return curTime in startTime..endTime

    }

}