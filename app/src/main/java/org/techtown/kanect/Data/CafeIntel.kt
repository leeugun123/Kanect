package org.techtown.kanect.Data

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

data class CafeIntel(

    val cafeImg : String = "",
    //카페 로고 이미지

    val cafeName : String = "",
    //카페 이름
    val seat : Int = 0,
    //카페 전체 좌석
    val allHours : Boolean = false,
    //24시간 유무
    val opExist : Boolean = false,
    //현재 영업 우무
    val cur_seat : Int = 0,
    //현재 카페 좌석
    val myCafe : Boolean = false,
    //내 카페 확인 유무
    val operTime : String = "",
    //영업 시간
    val plugSeat : Int = 0
    //플러그 좌석 수

) : Parcelable {
    constructor(parcel: Parcel) : this(

        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte(),
        parcel.readInt(),
        parcel.readByte() != 0.toByte(),
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
        parcel.writeByte(if (myCafe) 1 else 0)
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

