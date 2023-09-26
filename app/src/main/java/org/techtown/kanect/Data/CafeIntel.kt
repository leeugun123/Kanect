package org.techtown.kanect.Data

import android.os.Parcel
import android.os.Parcelable
import kotlinx.coroutines.Job

data class CafeIntel(

    var cafeImg: String = "",
    //카페 로고 이미지
    var cafeName: String = "",
    //카페 이름
    var seat: Int = 0,
    //카페 전체 좌석
    var allHours: Boolean = false,
    //24시간 유무
    var opExist: Boolean = false,
    //현재 영업 우무
    var cur_seat: Int = 0,
    //현재 카페 좌석
    var operTime: String = "",
    //영업 시간
    var plugSeat: Int = 0
    //플러그 좌석 수

) : Parcelable {
    constructor(parcel: Parcel) : this(

        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte(),
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readInt()

    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(cafeImg)
        parcel.writeString(cafeName)
        parcel.writeInt(seat)
        parcel.writeByte(if (allHours) 1 else 0)
        parcel.writeByte(if (opExist) 1 else 0)
        parcel.writeInt(cur_seat)
        parcel.writeString(operTime)
        parcel.writeInt(plugSeat)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CafeIntel> {
        override fun createFromParcel(parcel: Parcel): CafeIntel {
            return CafeIntel(parcel)
        }

        override fun newArray(size: Int): Array<CafeIntel?> {
            return arrayOfNulls(size)
        }
    }
}

